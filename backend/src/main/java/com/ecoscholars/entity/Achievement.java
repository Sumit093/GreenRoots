package com.ecoscholars.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Achievement entity for gamification system
 */
@Data
@Entity
@Table(name = "achievements")
@EqualsAndHashCode(callSuper = true)
public class Achievement extends BaseEntity {

    @Column(name = "type", nullable = false, length = 50)
    private String type; // POINTS_MILESTONE, YEAR_MILESTONE, CARE_STREAK, SPECIAL_EVENT

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "icon_url", length = 500)
    private String iconUrl;

    @Column(name = "points_awarded")
    private Integer pointsAwarded = 0;

    @Column(name = "badge_color", length = 20)
    private String badgeColor = "#4CAF50";

    @Column(name = "criteria", length = 1000)
    private String criteria;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "awarded_date", nullable = false)
    private LocalDate awardedDate;

    @Column(name = "awarded_by", length = 255)
    private String awardedBy;

    // Many-to-One relationship with Student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}
