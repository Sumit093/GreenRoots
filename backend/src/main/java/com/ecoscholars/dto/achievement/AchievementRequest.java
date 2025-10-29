package com.ecoscholars.dto.achievement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AchievementRequest {

    @NotBlank(message = "Achievement type is required")
    @Size(max = 50, message = "Type cannot exceed 50 characters")
    private String type; // POINTS_MILESTONE, YEAR_MILESTONE, CARE_STREAK, SPECIAL_EVENT

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Size(max = 500, message = "Icon URL cannot exceed 500 characters")
    private String iconUrl;

    @NotNull(message = "Points awarded is required")
    private Integer pointsAwarded;

    @Size(max = 20, message = "Badge color cannot exceed 20 characters")
    private String badgeColor;

    @Size(max = 1000, message = "Criteria cannot exceed 1000 characters")
    private String criteria;

    private Boolean isActive;

    @NotNull(message = "Awarded date is required")
    @PastOrPresent(message = "Awarded date cannot be in the future")
    private LocalDate awardedDate;

    @NotBlank(message = "Awarded by is required")
    @Size(max = 255, message = "Awarded by cannot exceed 255 characters")
    private String awardedBy;

    @NotNull(message = "Student ID is required")
    private Long studentId;
}
