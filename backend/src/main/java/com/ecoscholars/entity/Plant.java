package com.ecoscholars.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/**
 * Plant entity representing individual plants assigned to students
 */
@Data
@Entity
@Table(name = "plants")
@EqualsAndHashCode(callSuper = true)
public class Plant extends BaseEntity {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "scientific_name", length = 255)
    private String scientificName;

    @Column(name = "species", length = 100)
    private String species;

    @Column(name = "variety", length = 100)
    private String variety;

    @Column(name = "date_of_plantation", nullable = false)
    private LocalDate dateOfPlantation;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "soil_type", length = 100)
    private String soilType;

    @Column(name = "water_requirements", length = 255)
    private String waterRequirements;

    @Column(name = "sunlight_requirements", length = 255)
    private String sunlightRequirements;

    @Column(name = "ecological_importance", length = 1000)
    private String ecologicalImportance;

    @Column(name = "current_height")
    private Double currentHeight;

    @Column(name = "current_width")
    private Double currentWidth;

    @Column(name = "health_status", length = 50)
    private String healthStatus = "HEALTHY"; // HEALTHY, NEEDS_CARE, SICK, DEAD

    @Column(name = "last_watered_date")
    private LocalDate lastWateredDate;

    @Column(name = "last_fertilized_date")
    private LocalDate lastFertilizedDate;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "is_transferred")
    private Boolean isTransferred = false;

    // Many-to-One relationship with School
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    // One-to-One relationship with Student (current owner)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_student_id")
    private Student assignedStudent;

    // One-to-Many relationship with Growth Records
    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GrowthRecord> growthRecords;

    // One-to-Many relationship with Plant Transfers
    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlantTransfer> transfers;

    // Helper method to calculate plant age in years
    public int getPlantAgeInYears() {
        return LocalDate.now().getYear() - dateOfPlantation.getYear();
    }

    // Helper method to calculate plant age in days
    public long getPlantAgeInDays() {
        return java.time.temporal.ChronoUnit.DAYS.between(dateOfPlantation, LocalDate.now());
    }
}

