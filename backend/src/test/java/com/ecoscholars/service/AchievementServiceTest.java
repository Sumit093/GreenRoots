package com.ecoscholars.service;

import com.ecoscholars.dto.achievement.AchievementRequest;
import com.ecoscholars.dto.achievement.AchievementResponse;
import com.ecoscholars.entity.Achievement;
import com.ecoscholars.entity.Student;
import com.ecoscholars.exception.ResourceNotFoundException;
import com.ecoscholars.repository.AchievementRepository;
import com.ecoscholars.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {

    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private AchievementService achievementService;

    private AchievementRequest request;
    private Student student;
    private Achievement achievement;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setFirstName("Test");
        student.setLastName("Student");
        student.setGreenPoints(100);

        achievement = new Achievement();
        achievement.setId(1L);
        achievement.setType("POINTS_MILESTONE");
        achievement.setTitle("Green Starter");
        achievement.setPointsAwarded(100);
        achievement.setAwardedDate(LocalDate.now());
        achievement.setStudent(student);
        achievement.setIsActive(true);
        achievement.setCreatedAt(LocalDateTime.now());
        achievement.setUpdatedAt(LocalDateTime.now());

        request = new AchievementRequest();
        request.setStudentId(1L);
        request.setType("POINTS_MILESTONE");
        request.setTitle("Green Achiever");
        request.setPointsAwarded(50);
        request.setAwardedDate(LocalDate.now());
        request.setAwardedBy("System");
    }

    @Test
    void createAchievement_success() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(achievementRepository.save(any(Achievement.class))).thenReturn(achievement);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        AchievementResponse response = achievementService.createAchievement(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Green Starter", response.getTitle());
        assertEquals(150, student.getGreenPoints()); // Initial 100 + 50 from request
        verify(achievementRepository, times(1)).save(any(Achievement.class));
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void createAchievement_studentNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> achievementService.createAchievement(request));
        verify(achievementRepository, never()).save(any(Achievement.class));
    }

    @Test
    void getAchievementById_success() {
        when(achievementRepository.findById(anyLong())).thenReturn(Optional.of(achievement));

        AchievementResponse response = achievementService.getAchievementById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void deleteAchievement_success() {
        when(achievementRepository.findById(anyLong())).thenReturn(Optional.of(achievement));
        when(achievementRepository.save(any(Achievement.class))).thenReturn(achievement);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        achievementService.deleteAchievement(1L);

        assertFalse(achievement.getIsActive());
        assertEquals(0, student.getGreenPoints()); // 100 (initial) - 100 (deleted achievement)
        verify(achievementRepository, times(1)).save(achievement);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void updateAchievement_success() {
        AchievementRequest updateRequest = new AchievementRequest();
        updateRequest.setPointsAwarded(200);
        updateRequest.setTitle("Super Achiever");

        Achievement updatedAchievement = new Achievement();
        updatedAchievement.setId(1L);
        updatedAchievement.setType("POINTS_MILESTONE");
        updatedAchievement.setTitle("Super Achiever");
        updatedAchievement.setPointsAwarded(200);
        updatedAchievement.setAwardedDate(LocalDate.now());
        updatedAchievement.setStudent(student);
        updatedAchievement.setIsActive(true);
        updatedAchievement.setCreatedAt(LocalDateTime.now());
        updatedAchievement.setUpdatedAt(LocalDateTime.now());

        when(achievementRepository.findById(anyLong())).thenReturn(Optional.of(achievement));
        when(achievementRepository.save(any(Achievement.class))).thenReturn(updatedAchievement);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        AchievementResponse response = achievementService.updateAchievement(1L, updateRequest);

        assertNotNull(response);
        assertEquals("Super Achiever", response.getTitle());
        assertEquals(200, response.getPointsAwarded());
        assertEquals(200, student.getGreenPoints()); // (100-100)+200
        verify(achievementRepository, times(1)).save(any(Achievement.class));
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void getAchievementsByStudent_success() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(achievementRepository.findByStudentIdAndIsActiveTrueOrderByAwardedDateDesc(anyLong()))
                .thenReturn(Arrays.asList(achievement));

        List<AchievementResponse> responses = achievementService.getAchievementsByStudent(1L);

        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
    }
}
