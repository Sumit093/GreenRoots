package com.ecoscholars.service;

import com.ecoscholars.dto.growthrecord.GrowthRecordRequest;
import com.ecoscholars.dto.growthrecord.GrowthRecordResponse;
import com.ecoscholars.entity.GrowthRecord;
import com.ecoscholars.entity.Plant;
import com.ecoscholars.entity.Student;
import com.ecoscholars.exception.ResourceNotFoundException;
import com.ecoscholars.repository.GrowthRecordRepository;
import com.ecoscholars.repository.PlantRepository;
import com.ecoscholars.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GrowthRecordService {

    private final GrowthRecordRepository growthRecordRepository;
    private final PlantRepository plantRepository;
    private final StudentRepository studentRepository;
    private final CloudinaryService cloudinaryService;

    @Transactional
    public GrowthRecordResponse createGrowthRecord(GrowthRecordRequest request) {
        Plant plant = plantRepository.findById(request.getPlantId())
                .orElseThrow(() -> new ResourceNotFoundException("Plant", "id", request.getPlantId()));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", request.getStudentId()));

        String photoUrl = null;
        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            photoUrl = uploadImage(request.getImageFile());
        }

        GrowthRecord growthRecord = new GrowthRecord();
        growthRecord.setYear(request.getYear());
        growthRecord.setHeight(request.getHeight());
        growthRecord.setWidth(request.getWidth());
        growthRecord.setTrunkDiameter(request.getTrunkDiameter());
        growthRecord.setLeafCount(request.getLeafCount());
        growthRecord.setFlowerCount(request.getFlowerCount());
        growthRecord.setFruitCount(request.getFruitCount());
        growthRecord.setHealthStatus(request.getHealthStatus());
        growthRecord.setWeatherConditions(request.getWeatherConditions());
        growthRecord.setCareActivities(request.getCareActivities());
        growthRecord.setChallengesFaced(request.getChallengesFaced());
        growthRecord.setAchievements(request.getAchievements());
        growthRecord.setNotes(request.getNotes());
        growthRecord.setPhotoUrl(photoUrl);
        growthRecord.setPhotoCaption(request.getPhotoCaption());
        growthRecord.setRecordDate(request.getRecordDate());
        growthRecord.setIsVerified(false);
        growthRecord.setPlant(plant);
        growthRecord.setStudent(student);
        growthRecord.setIsActive(true);
        growthRecord.setCreatedAt(LocalDateTime.now());
        growthRecord.setUpdatedAt(LocalDateTime.now());

        GrowthRecord savedRecord = growthRecordRepository.save(growthRecord);

        // Update plant current stats
        plant.setCurrentHeight(request.getHeight());
        plant.setCurrentWidth(request.getWidth());
        plant.setHealthStatus(request.getHealthStatus());
        plant.setUpdatedAt(LocalDateTime.now());
        plantRepository.save(plant);

        // Award green points to student (example logic)
        student.setGreenPoints(student.getGreenPoints() + 10);
        studentRepository.save(student);

        return convertToDto(savedRecord);
    }

    @Transactional(readOnly = true)
    public GrowthRecordResponse getGrowthRecordById(Long id) {
        GrowthRecord record = growthRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GrowthRecord", "id", id));
        return convertToDto(record);
    }

    @Transactional(readOnly = true)
    public List<GrowthRecordResponse> getGrowthRecordsByPlant(Long plantId) {
        plantRepository.findById(plantId)
                .orElseThrow(() -> new ResourceNotFoundException("Plant", "id", plantId));

        return growthRecordRepository.findByPlantIdAndIsActiveTrueOrderByRecordDateDesc(plantId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GrowthRecordResponse> getGrowthRecordsByStudent(Long studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        return growthRecordRepository.findByStudentIdAndIsActiveTrueOrderByRecordDateDesc(studentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public GrowthRecordResponse updateGrowthRecord(Long id, GrowthRecordRequest request) {
        GrowthRecord record = growthRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GrowthRecord", "id", id));

        // Update fields if present in request
        if (request.getYear() != null) record.setYear(request.getYear());
        if (request.getHeight() != null) record.setHeight(request.getHeight());
        if (request.getWidth() != null) record.setWidth(request.getWidth());
        if (request.getTrunkDiameter() != null) record.setTrunkDiameter(request.getTrunkDiameter());
        if (request.getLeafCount() != null) record.setLeafCount(request.getLeafCount());
        if (request.getFlowerCount() != null) record.setFlowerCount(request.getFlowerCount());
        if (request.getFruitCount() != null) record.setFruitCount(request.getFruitCount());
        if (request.getHealthStatus() != null) record.setHealthStatus(request.getHealthStatus());
        if (request.getWeatherConditions() != null) record.setWeatherConditions(request.getWeatherConditions());
        if (request.getCareActivities() != null) record.setCareActivities(request.getCareActivities());
        if (request.getChallengesFaced() != null) record.setChallengesFaced(request.getChallengesFaced());
        if (request.getAchievements() != null) record.setAchievements(request.getAchievements());
        if (request.getNotes() != null) record.setNotes(request.getNotes());
        if (request.getPhotoCaption() != null) record.setPhotoCaption(request.getPhotoCaption());
        if (request.getRecordDate() != null) record.setRecordDate(request.getRecordDate());
        // Verification fields would typically be updated by an admin, not via this generic update

        // Handle image file update if provided
        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            if (record.getPhotoUrl() != null) {
                // Delete old image from Cloudinary if it exists
                String publicId = extractPublicIdFromUrl(record.getPhotoUrl());
                cloudinaryService.deleteFile(publicId);
            }
            record.setPhotoUrl(uploadImage(request.getImageFile()));
        }

        record.setUpdatedAt(LocalDateTime.now());

        GrowthRecord updatedRecord = growthRecordRepository.save(record);

        // Update plant current stats based on the latest record
        Plant plant = updatedRecord.getPlant();
        plant.setCurrentHeight(updatedRecord.getHeight());
        plant.setCurrentWidth(updatedRecord.getWidth());
        plant.setHealthStatus(updatedRecord.getHealthStatus());
        plant.setUpdatedAt(LocalDateTime.now());
        plantRepository.save(plant);

        return convertToDto(updatedRecord);
    }

    @Transactional
    public void deleteGrowthRecord(Long id) {
        GrowthRecord record = growthRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GrowthRecord", "id", id));

        record.setIsActive(false);
        record.setUpdatedAt(LocalDateTime.now());
        growthRecordRepository.save(record);

        // Optionally delete image from Cloudinary
        if (record.getPhotoUrl() != null) {
            String publicId = extractPublicIdFromUrl(record.getPhotoUrl());
            cloudinaryService.deleteFile(publicId);
        }
    }

    @Transactional
    public GrowthRecordResponse verifyGrowthRecord(Long id, String verifiedBy) {
        GrowthRecord record = growthRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GrowthRecord", "id", id));
        
        record.setIsVerified(true);
        record.setVerifiedBy(verifiedBy);
        record.setVerificationDate(LocalDate.now());
        record.setUpdatedAt(LocalDateTime.now());

        return convertToDto(growthRecordRepository.save(record));
    }

    private String uploadImage(MultipartFile imageFile) {
        try {
            Map uploadResult = cloudinaryService.uploadFile(imageFile);
            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image to Cloudinary", e);
        }
    }

    private String extractPublicIdFromUrl(String url) {
        // Example: https://res.cloudinary.com/your_cloud_name/image/upload/v1234567890/folder/publicId.jpg
        int lastSlash = url.lastIndexOf('/');
        int lastDot = url.lastIndexOf('.');
        if (lastSlash != -1 && lastDot != -1 && lastDot > lastSlash) {
            return url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.'));
        }
        return url; // Fallback, might not be a public ID
    }

    private GrowthRecordResponse convertToDto(GrowthRecord record) {
        GrowthRecordResponse dto = new GrowthRecordResponse();
        dto.setId(record.getId());
        dto.setYear(record.getYear());
        dto.setHeight(record.getHeight());
        dto.setWidth(record.getWidth());
        dto.setTrunkDiameter(record.getTrunkDiameter());
        dto.setLeafCount(record.getLeafCount());
        dto.setFlowerCount(record.getFlowerCount());
        dto.setFruitCount(record.getFruitCount());
        dto.setHealthStatus(record.getHealthStatus());
        dto.setWeatherConditions(record.getWeatherConditions());
        dto.setCareActivities(record.getCareActivities());
        dto.setChallengesFaced(record.getChallengesFaced());
        dto.setAchievements(record.getAchievements());
        dto.setNotes(record.getNotes());
        dto.setPhotoUrl(record.getPhotoUrl());
        dto.setPhotoCaption(record.getPhotoCaption());
        dto.setRecordDate(record.getRecordDate());
        dto.setIsVerified(record.getIsVerified());
        dto.setVerifiedBy(record.getVerifiedBy());
        dto.setVerificationDate(record.getVerificationDate());
        dto.setIsActive(record.getIsActive());
        dto.setCreatedAt(record.getCreatedAt());
        dto.setUpdatedAt(record.getUpdatedAt());
        if (record.getPlant() != null) {
            dto.setPlantId(record.getPlant().getId());
            dto.setPlantName(record.getPlant().getName());
        }
        if (record.getStudent() != null) {
            dto.setStudentId(record.getStudent().getId());
            dto.setStudentName(record.getStudent().getFullName());
        }
        return dto;
    }
}
