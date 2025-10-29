package com.ecoscholars.dto.plant;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PlantResponse {
    private Long id;
    private String name;
    private String scientificName;
    private String species;
    private String variety;
    private LocalDate dateOfPlantation;
    private String location;
    private String soilType;
    private String waterRequirements;
    private String sunlightRequirements;
    private String ecologicalImportance;
    private Double currentHeight;
    private Double currentWidth;
    private String healthStatus;
    private LocalDate lastWateredDate;
    private LocalDate lastFertilizedDate;
    private String notes;
    private Boolean isTransferred;
    private Boolean isActive;
    private Long schoolId;
    private String schoolName;
    private Long assignedStudentId;
    private String assignedStudentName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
