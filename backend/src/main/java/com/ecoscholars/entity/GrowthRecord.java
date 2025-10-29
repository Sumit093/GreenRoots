package com.ecoscholars.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Growth Record entity for tracking yearly plant growth
 */
@Data
@Entity
@Table(name = "growth_records")
@EqualsAndHashCode(callSuper = true)
public class GrowthRecord extends BaseEntity {

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "height")
    private Double height;

    @Column(name = "width")
    private Double width;

    @Column(name = "trunk_diameter")
    private Double trunkDiameter;

    @Column(name = "leaf_count")
    private Integer leafCount;

    @Column(name = "flower_count")
    private Integer flowerCount;

    @Column(name = "fruit_count")
    private Integer fruitCount;

    @Column(name = "health_status", length = 50)
    private String healthStatus;

    @Column(name = "weather_conditions", length = 255)
    private String weatherConditions;

    @Column(name = "care_activities", length = 1000)
    private String careActivities;

    @Column(name = "challenges_faced", length = 1000)
    private String challengesFaced;

    @Column(name = "achievements", length = 1000)
    private String achievements;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(name = "photo_caption", length = 255)
    private String photoCaption;

    @Column(name = "record_date")
    private LocalDate recordDate;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "verified_by", length = 255)
    private String verifiedBy;

    @Column(name = "verification_date")
    private LocalDate verificationDate;

    // Many-to-One relationship with Plant
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    // Many-to-One relationship with Student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}

