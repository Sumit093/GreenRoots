package com.ecoscholars.controller;

import com.ecoscholars.dto.achievement.AchievementRequest;
import com.ecoscholars.dto.achievement.AchievementResponse;
import com.ecoscholars.service.AchievementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achievements")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<AchievementResponse> createAchievement(@Valid @RequestBody AchievementRequest request) {
        AchievementResponse newAchievement = achievementService.createAchievement(request);
        return new ResponseEntity<>(newAchievement, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<List<AchievementResponse>> getAllAchievements() {
        List<AchievementResponse> achievements = achievementService.getAllAchievements();
        return ResponseEntity.ok(achievements);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<AchievementResponse> getAchievementById(@PathVariable Long id) {
        AchievementResponse achievement = achievementService.getAchievementById(id);
        return ResponseEntity.ok(achievement);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<AchievementResponse> updateAchievement(@PathVariable Long id, @Valid @RequestBody AchievementRequest request) {
        AchievementResponse updatedAchievement = achievementService.updateAchievement(id, request);
        return ResponseEntity.ok(updatedAchievement);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<List<AchievementResponse>> getAchievementsByStudent(@PathVariable Long studentId) {
        List<AchievementResponse> achievements = achievementService.getAchievementsByStudent(studentId);
        return ResponseEntity.ok(achievements);
    }
}
