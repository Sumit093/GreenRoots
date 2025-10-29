package com.ecoscholars.service;

import com.ecoscholars.dto.planttransfer.PlantTransferRequest;
import com.ecoscholars.dto.planttransfer.PlantTransferResponse;
import com.ecoscholars.entity.Plant;
import com.ecoscholars.entity.PlantTransfer;
import com.ecoscholars.entity.Student;
import com.ecoscholars.exception.ResourceNotFoundException;
import com.ecoscholars.repository.PlantRepository;
import com.ecoscholars.repository.PlantTransferRepository;
import com.ecoscholars.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantTransferService {

    private final PlantTransferRepository plantTransferRepository;
    private final PlantRepository plantRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public PlantTransferResponse initiatePlantTransfer(PlantTransferRequest request) {
        Plant plant = plantRepository.findById(request.getPlantId())
                .orElseThrow(() -> new ResourceNotFoundException("Plant", "id", request.getPlantId()));

        Student previousStudent = studentRepository.findById(request.getPreviousStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Previous Student", "id", request.getPreviousStudentId()));

        if (!plant.getAssignedStudent().getId().equals(previousStudent.getId())) {
            throw new IllegalArgumentException("Plant is not currently assigned to the previous student.");
        }

        Student newStudent = null;
        if (request.getNewStudentId() != null) {
            newStudent = studentRepository.findById(request.getNewStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("New Student", "id", request.getNewStudentId()));
            if (newStudent.getAssignedPlant() != null) {
                throw new IllegalArgumentException("New student already has an assigned plant.");
            }
        }

        PlantTransfer transfer = new PlantTransfer();
        transfer.setPlant(plant);
        transfer.setPreviousStudent(previousStudent);
        transfer.setNewStudent(newStudent);
        transfer.setTransferDate(request.getTransferDate());
        transfer.setReason(request.getReason());
        transfer.setTransferNotes(request.getTransferNotes());
        transfer.setPlantCondition(request.getPlantCondition());
        transfer.setHandoverNotes(request.getHandoverNotes());
        transfer.setIsCompleted(false);
        transfer.setInitiatedBy(request.getInitiatedBy());
        transfer.setIsActive(true);
        transfer.setCreatedAt(LocalDateTime.now());
        transfer.setUpdatedAt(LocalDateTime.now());

        PlantTransfer savedTransfer = plantTransferRepository.save(transfer);

        // Update plant and student assignments
        plant.setIsTransferred(true);
        plant.setAssignedStudent(newStudent);
        plant.setUpdatedAt(LocalDateTime.now());
        plantRepository.save(plant);

        previousStudent.setAssignedPlant(null);
        previousStudent.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(previousStudent);

        if (newStudent != null) {
            newStudent.setAssignedPlant(plant);
            newStudent.setUpdatedAt(LocalDateTime.now());
            studentRepository.save(newStudent);
        }

        return convertToDto(savedTransfer);
    }

    @Transactional(readOnly = true)
    public PlantTransferResponse getPlantTransferById(Long id) {
        PlantTransfer transfer = plantTransferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PlantTransfer", "id", id));
        return convertToDto(transfer);
    }

    @Transactional(readOnly = true)
    public List<PlantTransferResponse> getAllPlantTransfers() {
        return plantTransferRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PlantTransferResponse> getPlantTransfersByPlant(Long plantId) {
        plantRepository.findById(plantId)
                .orElseThrow(() -> new ResourceNotFoundException("Plant", "id", plantId));
        return plantTransferRepository.findByPlantIdAndIsActiveTrueOrderByTransferDateDesc(plantId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PlantTransferResponse> getPlantTransfersByPreviousStudent(Long studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
        return plantTransferRepository.findByPreviousStudentIdAndIsActiveTrueOrderByTransferDateDesc(studentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PlantTransferResponse> getPlantTransfersByNewStudent(Long studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
        return plantTransferRepository.findByNewStudentIdAndIsActiveTrueOrderByTransferDateDesc(studentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PlantTransferResponse updatePlantTransfer(Long id, PlantTransferRequest request) {
        PlantTransfer transfer = plantTransferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PlantTransfer", "id", id));

        if (request.getTransferDate() != null) transfer.setTransferDate(request.getTransferDate());
        if (request.getReason() != null) transfer.setReason(request.getReason());
        if (request.getTransferNotes() != null) transfer.setTransferNotes(request.getTransferNotes());
        if (request.getPlantCondition() != null) transfer.setPlantCondition(request.getPlantCondition());
        if (request.getHandoverNotes() != null) transfer.setHandoverNotes(request.getHandoverNotes());
        if (request.getIsCompleted() != null) transfer.setIsCompleted(request.getIsCompleted());
        if (request.getCompletedDate() != null) transfer.setCompletedDate(request.getCompletedDate());
        if (request.getInitiatedBy() != null) transfer.setInitiatedBy(request.getInitiatedBy());
        if (request.getApprovedBy() != null) transfer.setApprovedBy(request.getApprovedBy());
        if (request.getApprovalDate() != null) transfer.setApprovalDate(request.getApprovalDate());

        transfer.setUpdatedAt(LocalDateTime.now());
        PlantTransfer updatedTransfer = plantTransferRepository.save(transfer);

        return convertToDto(updatedTransfer);
    }

    @Transactional
    public void deletePlantTransfer(Long id) {
        PlantTransfer transfer = plantTransferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PlantTransfer", "id", id));

        transfer.setIsActive(false);
        transfer.setUpdatedAt(LocalDateTime.now());
        plantTransferRepository.save(transfer);
    }

    private PlantTransferResponse convertToDto(PlantTransfer transfer) {
        PlantTransferResponse dto = new PlantTransferResponse();
        dto.setId(transfer.getId());
        dto.setTransferDate(transfer.getTransferDate());
        dto.setReason(transfer.getReason());
        dto.setTransferNotes(transfer.getTransferNotes());
        dto.setPlantCondition(transfer.getPlantCondition());
        dto.setHandoverNotes(transfer.getHandoverNotes());
        dto.setIsCompleted(transfer.getIsCompleted());
        dto.setCompletedDate(transfer.getCompletedDate());
        dto.setInitiatedBy(transfer.getInitiatedBy());
        dto.setApprovedBy(transfer.getApprovedBy());
        dto.setApprovalDate(transfer.getApprovalDate());
        dto.setIsActive(transfer.getIsActive());
        dto.setCreatedAt(transfer.getCreatedAt());
        dto.setUpdatedAt(transfer.getUpdatedAt());
        if (transfer.getPlant() != null) {
            dto.setPlantId(transfer.getPlant().getId());
            dto.setPlantName(transfer.getPlant().getName());
        }
        if (transfer.getPreviousStudent() != null) {
            dto.setPreviousStudentId(transfer.getPreviousStudent().getId());
            dto.setPreviousStudentName(transfer.getPreviousStudent().getFullName());
        }
        if (transfer.getNewStudent() != null) {
            dto.setNewStudentId(transfer.getNewStudent().getId());
            dto.setNewStudentName(transfer.getNewStudent().getFullName());
        }
        return dto;
    }
}
