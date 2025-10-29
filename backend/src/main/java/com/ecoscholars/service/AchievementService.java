package com.ecoscholars.service;

import com.ecoscholars.dto.achievement.AchievementRequest;
import com.ecoscholars.dto.achievement.AchievementResponse;
import com.ecoscholars.entity.Achievement;
import com.ecoscholars.entity.Student;
import com.ecoscholars.exception.ResourceNotFoundException;
import com.ecoscholars.repository.AchievementRepository;
import com.ecoscholars.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public AchievementResponse createAchievement(AchievementRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", request.getStudentId()));

        Achievement achievement = new Achievement();
        achievement.setType(request.getType());
        achievement.setTitle(request.getTitle());
        achievement.setDescription(request.getDescription());
        achievement.setIconUrl(request.getIconUrl());
        achievement.setPointsAwarded(request.getPointsAwarded());
        achievement.setBadgeColor(request.getBadgeColor());
        achievement.setCriteria(request.getCriteria());
        achievement.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        achievement.setAwardedDate(request.getAwardedDate());
        achievement.setAwardedBy(request.getAwardedBy());
        achievement.setStudent(student);
        achievement.setCreatedAt(LocalDateTime.now());
        achievement.setUpdatedAt(LocalDateTime.now());

        Achievement savedAchievement = achievementRepository.save(achievement);

        // Update student's green points
        student.setGreenPoints(student.getGreenPoints() + request.getPointsAwarded());
        student.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(student);

        return convertToDto(savedAchievement);
    }

    @Transactional(readOnly = true)
    public AchievementResponse getAchievementById(Long id) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement", "id", id));
        return convertToDto(achievement);
    }

    @Transactional(readOnly = true)
    public List<AchievementResponse> getAllAchievements() {
        return achievementRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AchievementResponse> getAchievementsByStudent(Long studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        return achievementRepository.findByStudentIdAndIsActiveTrueOrderByAwardedDateDesc(studentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AchievementResponse updateAchievement(Long id, AchievementRequest request) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement", "id", id));

        Integer oldPoints = achievement.getPointsAwarded();

        if (request.getType() != null) achievement.setType(request.getType());
        if (request.getTitle() != null) achievement.setTitle(request.getTitle());
        if (request.getDescription() != null) achievement.setDescription(request.getDescription());
        if (request.getIconUrl() != null) achievement.setIconUrl(request.getIconUrl());
        if (request.getPointsAwarded() != null) achievement.setPointsAwarded(request.getPointsAwarded());
        if (request.getBadgeColor() != null) achievement.setBadgeColor(request.getBadgeColor());
        if (request.getCriteria() != null) achievement.setCriteria(request.getCriteria());
        if (request.getIsActive() != null) achievement.setIsActive(request.getIsActive());
        if (request.getAwardedDate() != null) achievement.setAwardedDate(request.getAwardedDate());
        if (request.getAwardedBy() != null) achievement.setAwardedBy(request.getAwardedBy());

        achievement.setUpdatedAt(LocalDateTime.now());
        Achievement updatedAchievement = achievementRepository.save(achievement);

        // Update student's green points if points awarded changed
        if (request.getPointsAwarded() != null && !oldPoints.equals(request.getPointsAwarded())) {
            Student student = updatedAchievement.getStudent();
            student.setGreenPoints(student.getGreenPoints() - oldPoints + updatedAchievement.getPointsAwarded());
            student.setUpdatedAt(LocalDateTime.now());
            studentRepository.save(student);
        }

        return convertToDto(updatedAchievement);
    }

    @Transactional
    public void deleteAchievement(Long id) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement", "id", id));

        achievement.setIsActive(false);
        achievement.setUpdatedAt(LocalDateTime.now());
        achievementRepository.save(achievement);

        // Deduct green points from student
        Student student = achievement.getStudent();
        student.setGreenPoints(student.getGreenPoints() - achievement.getPointsAwarded());
        student.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(student);
    }

    private AchievementResponse convertToDto(Achievement achievement) {
        AchievementResponse dto = new AchievementResponse();
        dto.setId(achievement.getId());
        dto.setType(achievement.getType());
        dto.setTitle(achievement.getTitle());
        dto.setDescription(achievement.getDescription());
        dto.setIconUrl(achievement.getIconUrl());
        dto.setPointsAwarded(achievement.getPointsAwarded());
        dto.setBadgeColor(achievement.getBadgeColor());
        dto.setCriteria(achievement.getCriteria());
        dto.setIsActive(achievement.getIsActive());
        dto.setAwardedDate(achievement.getAwardedDate());
        dto.setAwardedBy(achievement.getAwardedBy());
        dto.setCreatedAt(achievement.getCreatedAt());
        dto.setUpdatedAt(achievement.getUpdatedAt());
        if (achievement.getStudent() != null) {
            dto.setStudentId(achievement.getStudent().getId());
            dto.setStudentName(achievement.getStudent().getFullName());
        }
        return dto;
    }
}
