package com.ecoscholars.dto.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentUpdateRequest {

    @Size(max = 100, message = "First name cannot exceed 100 characters")
    private String firstName;

    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String lastName;

    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Size(max = 10, message = "Grade cannot exceed 10 characters")
    private String grade;

    @Size(max = 50, message = "Roll number cannot exceed 50 characters")
    private String rollNumber;

    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    private String phone;

    @Size(max = 255, message = "Parent name cannot exceed 255 characters")
    private String parentName;

    @Size(max = 20, message = "Parent phone number cannot exceed 20 characters")
    private String parentPhone;

    @Email(message = "Invalid parent email format")
    @Size(max = 255, message = "Parent email cannot exceed 255 characters")
    private String parentEmail;

    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    @Past(message = "Enrollment date must be in the past")
    private LocalDate enrollmentDate;

    private Boolean isGraduated;

    private LocalDate graduationDate;

    private Integer greenPoints;

    private Boolean isActive;

    private Long schoolId;
}
