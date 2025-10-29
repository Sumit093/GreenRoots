package com.ecoscholars.dto.plant;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlantUpdateRequest {

    @Size(max = 255, message = "Plant name cannot exceed 255 characters")
    private String name;

    @Size(max = 255, message = "Scientific name cannot exceed 255 characters")
    private String scientificName;

    @Size(max = 100, message = "Species cannot exceed 100 characters")
    private String species;

    @Size(max = 100, message = "Variety cannot exceed 100 characters")
    private String variety;

    @PastOrPresent(message = "Date of plantation cannot be in the future")
    private LocalDate dateOfPlantation;

    @Size(max = 255, message = "Location cannot exceed 255 characters")
    private String location;

    @Size(max = 100, message = "Soil type cannot exceed 100 characters")
    private String soilType;

    @Size(max = 255, message = "Water requirements cannot exceed 255 characters")
    private String waterRequirements;

    @Size(max = 255, message = "Sunlight requirements cannot exceed 255 characters")
    private String sunlightRequirements;

    @Size(max = 1000, message = "Ecological importance cannot exceed 1000 characters")
    private String ecologicalImportance;

    private Double currentHeight;
    private Double currentWidth;

    @Size(max = 50, message = "Health status cannot exceed 50 characters")
    private String healthStatus;

    private LocalDate lastWateredDate;
    private LocalDate lastFertilizedDate;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    private Boolean isTransferred;
    private Boolean isActive;

    private Long schoolId;
    private Long assignedStudentId;
}
