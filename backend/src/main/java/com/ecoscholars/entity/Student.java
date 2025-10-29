package com.ecoscholars.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/**
 * Student entity representing individual students
 */
@Data
@Entity
@Table(name = "students")
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseEntity {

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "grade", length = 10)
    private String grade;

    @Column(name = "roll_number", length = 50)
    private String rollNumber;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "parent_name", length = 255)
    private String parentName;

    @Column(name = "parent_phone", length = 20)
    private String parentPhone;

    @Column(name = "parent_email", length = 255)
    private String parentEmail;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

    @Column(name = "is_graduated")
    private Boolean isGraduated = false;

    @Column(name = "graduation_date")
    private LocalDate graduationDate;

    @Column(name = "green_points")
    private Integer greenPoints = 0;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified = false;

    // Many-to-One relationship with School
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    // One-to-One relationship with Plant
    @OneToOne(mappedBy = "assignedStudent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Plant assignedPlant;

    // One-to-Many relationship with Growth Records
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GrowthRecord> growthRecords;

    // One-to-Many relationship with Achievements
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Achievement> achievements;

    // One-to-Many relationship with Plant Transfers (as previous owner)
    @OneToMany(mappedBy = "previousStudent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlantTransfer> previousTransfers;

    // One-to-Many relationship with Plant Transfers (as new owner)
    @OneToMany(mappedBy = "newStudent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlantTransfer> newTransfers;

    // Helper method to get full name
    public String getFullName() {
        return firstName + " " + lastName;
    }
}

