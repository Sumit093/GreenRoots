package com.ecoscholars.dto.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentRegistrationRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name cannot exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Grade is required")
    @Size(max = 10, message = "Grade cannot exceed 10 characters")
    private String grade;

    @NotBlank(message = "Roll number is required")
    @Size(max = 50, message = "Roll number cannot exceed 50 characters")
    private String rollNumber;

    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    private String phone;

    @NotBlank(message = "Parent name is required")
    @Size(max = 255, message = "Parent name cannot exceed 255 characters")
    private String parentName;

    @Size(max = 20, message = "Parent phone number cannot exceed 20 characters")
    private String parentPhone;

    @Email(message = "Invalid parent email format")
    @Size(max = 255, message = "Parent email cannot exceed 255 characters")
    private String parentEmail;

    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    @NotNull(message = "Enrollment date is required")
    @Past(message = "Enrollment date must be in the past")
    private LocalDate enrollmentDate;

    @NotNull(message = "School ID is required")
    private Long schoolId;
}
