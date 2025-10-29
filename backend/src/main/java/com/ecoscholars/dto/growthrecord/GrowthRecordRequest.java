package com.ecoscholars.dto.growthrecord;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class GrowthRecordRequest {

    @NotNull(message = "Year is required")
    private Integer year;

    private Double height;
    private Double width;
    private Double trunkDiameter;
    private Integer leafCount;
    private Integer flowerCount;
    private Integer fruitCount;

    @Size(max = 50, message = "Health status cannot exceed 50 characters")
    private String healthStatus;

    @Size(max = 255, message = "Weather conditions cannot exceed 255 characters")
    private String weatherConditions;

    @Size(max = 1000, message = "Care activities cannot exceed 1000 characters")
    private String careActivities;

    @Size(max = 1000, message = "Challenges faced cannot exceed 1000 characters")
    private String challengesFaced;

    @Size(max = 1000, message = "Achievements cannot exceed 1000 characters")
    private String achievements;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    @Size(max = 255, message = "Photo caption cannot exceed 255 characters")
    private String photoCaption;

    @NotNull(message = "Record date is required")
    @PastOrPresent(message = "Record date cannot be in the future")
    private LocalDate recordDate;

    @NotNull(message = "Plant ID is required")
    private Long plantId;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    // For file upload
    private MultipartFile imageFile;
}
