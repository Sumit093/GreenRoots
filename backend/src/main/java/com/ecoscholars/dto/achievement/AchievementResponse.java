package com.ecoscholars.dto.achievement;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AchievementResponse {
    private Long id;
    private String type;
    private String title;
    private String description;
    private String iconUrl;
    private Integer pointsAwarded;
    private String badgeColor;
    private String criteria;
    private Boolean isActive;
    private LocalDate awardedDate;
    private String awardedBy;
    private Long studentId;
    private String studentName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
