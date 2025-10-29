package com.ecoscholars.service;

import com.ecoscholars.dto.plant.PlantRegistrationRequest;
import com.ecoscholars.dto.plant.PlantResponse;
import com.ecoscholars.dto.plant.PlantUpdateRequest;
import com.ecoscholars.entity.Plant;
import com.ecoscholars.entity.School;
import com.ecoscholars.entity.Student;
import com.ecoscholars.exception.ResourceNotFoundException;
import com.ecoscholars.repository.PlantRepository;
import com.ecoscholars.repository.SchoolRepository;
import com.ecoscholars.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public PlantResponse registerPlant(PlantRegistrationRequest request) {
        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new ResourceNotFoundException("School", "id", request.getSchoolId()));

        Student assignedStudent = null;
        if (request.getAssignedStudentId() != null) {
            assignedStudent = studentRepository.findById(request.getAssignedStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student", "id", request.getAssignedStudentId()));
            if (assignedStudent.getAssignedPlant() != null) {
                throw new IllegalArgumentException("Student already has an assigned plant.");
            }
        }

        Plant plant = new Plant();
        plant.setName(request.getName());
        plant.setScientificName(request.getScientificName());
        plant.setSpecies(request.getSpecies());
        plant.setVariety(request.getVariety());
        plant.setDateOfPlantation(request.getDateOfPlantation());
        plant.setLocation(request.getLocation());
        plant.setSoilType(request.getSoilType());
        plant.setWaterRequirements(request.getWaterRequirements());
        plant.setSunlightRequirements(request.getSunlightRequirements());
        plant.setEcologicalImportance(request.getEcologicalImportance());
        plant.setCurrentHeight(request.getCurrentHeight());
        plant.setCurrentWidth(request.getCurrentWidth());
        plant.setHealthStatus(request.getHealthStatus() != null ? request.getHealthStatus() : "HEALTHY");
        plant.setLastWateredDate(request.getLastWateredDate());
        plant.setLastFertilizedDate(request.getLastFertilizedDate());
        plant.setNotes(request.getNotes());
        plant.setIsActive(true);
        plant.setIsTransferred(false);
        plant.setSchool(school);
        plant.setAssignedStudent(assignedStudent);
        plant.setCreatedAt(LocalDateTime.now());
        plant.setUpdatedAt(LocalDateTime.now());

        Plant savedPlant = plantRepository.save(plant);

        if (assignedStudent != null) {
            assignedStudent.setAssignedPlant(savedPlant);
            studentRepository.save(assignedStudent);
        }

        school.setTotalPlants(school.getTotalPlants() + 1);
        schoolRepository.save(school);

        return convertToDto(savedPlant);
    }

    @Transactional(readOnly = true)
    public PlantResponse getPlantById(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plant", "id", id));
        return convertToDto(plant);
    }

    @Transactional(readOnly = true)
    public List<PlantResponse> getAllPlants() {
        return plantRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PlantResponse> getPlantsBySchool(Long schoolId) {
        schoolRepository.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School", "id", schoolId));

        return plantRepository.findBySchoolIdAndIsActiveTrue(schoolId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PlantResponse> getPlantsByStudent(Long studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        return plantRepository.findByAssignedStudentIdAndIsActiveTrue(studentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PlantResponse updatePlant(Long id, PlantUpdateRequest request) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plant", "id", id));

        if (request.getName() != null) plant.setName(request.getName());
        if (request.getScientificName() != null) plant.setScientificName(request.getScientificName());
        if (request.getSpecies() != null) plant.setSpecies(request.getSpecies());
        if (request.getVariety() != null) plant.setVariety(request.getVariety());
        if (request.getDateOfPlantation() != null) plant.setDateOfPlantation(request.getDateOfPlantation());
        if (request.getLocation() != null) plant.setLocation(request.getLocation());
        if (request.getSoilType() != null) plant.setSoilType(request.getSoilType());
        if (request.getWaterRequirements() != null) plant.setWaterRequirements(request.getWaterRequirements());
        if (request.getSunlightRequirements() != null) plant.setSunlightRequirements(request.getSunlightRequirements());
        if (request.getEcologicalImportance() != null) plant.setEcologicalImportance(request.getEcologicalImportance());
        if (request.getCurrentHeight() != null) plant.setCurrentHeight(request.getCurrentHeight());
        if (request.getCurrentWidth() != null) plant.setCurrentWidth(request.getCurrentWidth());
        if (request.getHealthStatus() != null) plant.setHealthStatus(request.getHealthStatus());
        if (request.getLastWateredDate() != null) plant.setLastWateredDate(request.getLastWateredDate());
        if (request.getLastFertilizedDate() != null) plant.setLastFertilizedDate(request.getLastFertilizedDate());
        if (request.getNotes() != null) plant.setNotes(request.getNotes());
        if (request.getIsTransferred() != null) plant.setIsTransferred(request.getIsTransferred());
        if (request.getIsActive() != null) plant.setIsActive(request.getIsActive());

        if (request.getSchoolId() != null && !request.getSchoolId().equals(plant.getSchool().getId())) {
            School newSchool = schoolRepository.findById(request.getSchoolId())
                    .orElseThrow(() -> new ResourceNotFoundException("School", "id", request.getSchoolId()));
            plant.setSchool(newSchool);
        }

        if (request.getAssignedStudentId() != null) {
            Student newStudent = studentRepository.findById(request.getAssignedStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student", "id", request.getAssignedStudentId()));
            if (newStudent.getAssignedPlant() != null && !newStudent.getAssignedPlant().getId().equals(plant.getId())) {
                throw new IllegalArgumentException("Student already has an assigned plant.");
            }
            plant.setAssignedStudent(newStudent);
        } else if (request.getAssignedStudentId() == null && plant.getAssignedStudent() != null) {
            // Unassign student
            plant.setAssignedStudent(null);
        }
        plant.setUpdatedAt(LocalDateTime.now());

        Plant updatedPlant = plantRepository.save(plant);

        return convertToDto(updatedPlant);
    }

    @Transactional
    public void deletePlant(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plant", "id", id));

        plant.setIsActive(false);
        plant.setUpdatedAt(LocalDateTime.now());
        plantRepository.save(plant);

        // Remove assignment from student if any
        if (plant.getAssignedStudent() != null) {
            Student student = plant.getAssignedStudent();
            student.setAssignedPlant(null);
            studentRepository.save(student);
        }

        // Decrement plant count in school
        School school = plant.getSchool();
        if (school != null) {
            school.setTotalPlants(school.getTotalPlants() - 1);
            schoolRepository.save(school);
        }
    }

    private PlantResponse convertToDto(Plant plant) {
        PlantResponse dto = new PlantResponse();
        dto.setId(plant.getId());
        dto.setName(plant.getName());
        dto.setScientificName(plant.getScientificName());
        dto.setSpecies(plant.getSpecies());
        dto.setVariety(plant.getVariety());
        dto.setDateOfPlantation(plant.getDateOfPlantation());
        dto.setLocation(plant.getLocation());
        dto.setSoilType(plant.getSoilType());
        dto.setWaterRequirements(plant.getWaterRequirements());
        dto.setSunlightRequirements(plant.getSunlightRequirements());
        dto.setEcologicalImportance(plant.getEcologicalImportance());
        dto.setCurrentHeight(plant.getCurrentHeight());
        dto.setCurrentWidth(plant.getCurrentWidth());
        dto.setHealthStatus(plant.getHealthStatus());
        dto.setLastWateredDate(plant.getLastWateredDate());
        dto.setLastFertilizedDate(plant.getLastFertilizedDate());
        dto.setNotes(plant.getNotes());
        dto.setIsTransferred(plant.getIsTransferred());
        dto.setIsActive(plant.getIsActive());
        dto.setCreatedAt(plant.getCreatedAt());
        dto.setUpdatedAt(plant.getUpdatedAt());
        if (plant.getSchool() != null) {
            dto.setSchoolId(plant.getSchool().getId());
            dto.setSchoolName(plant.getSchool().getName());
        }
        if (plant.getAssignedStudent() != null) {
            dto.setAssignedStudentId(plant.getAssignedStudent().getId());
            dto.setAssignedStudentName(plant.getAssignedStudent().getFullName());
        }
        return dto;
    }
}
