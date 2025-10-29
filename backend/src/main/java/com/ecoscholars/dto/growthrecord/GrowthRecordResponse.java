package com.ecoscholars.dto.growthrecord;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class GrowthRecordResponse {
    private Long id;
    private Integer year;
    private Double height;
    private Double width;
    private Double trunkDiameter;
    private Integer leafCount;
    private Integer flowerCount;
    private Integer fruitCount;
    private String healthStatus;
    private String weatherConditions;
    private String careActivities;
    private String challengesFaced;
    private String achievements;
    private String notes;
    private String photoUrl;
    private String photoCaption;
    private LocalDate recordDate;
    private Boolean isVerified;
    private String verifiedBy;
    private LocalDate verificationDate;
    private Long plantId;
    private String plantName;
    private Long studentId;
    private String studentName;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
