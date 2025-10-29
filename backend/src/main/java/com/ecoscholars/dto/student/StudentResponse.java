package com.ecoscholars.dto.student;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String grade;
    private String rollNumber;
    private String phone;
    private String parentName;
    private String parentPhone;
    private String parentEmail;
    private String address;
    private LocalDate enrollmentDate;
    private Boolean isGraduated;
    private LocalDate graduationDate;
    private Integer greenPoints;
    private Boolean isEmailVerified;
    private Long schoolId;
    private String schoolName;
    private Long assignedPlantId;
    private String assignedPlantName;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
