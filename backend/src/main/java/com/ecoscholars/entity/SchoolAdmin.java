package com.ecoscholars.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * School Admin entity for school-level administrators
 */
@Data
@Entity
@Table(name = "school_admins")
@EqualsAndHashCode(callSuper = true)
public class SchoolAdmin extends BaseEntity {

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "designation", length = 100)
    private String designation;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified = false;

    @Column(name = "last_login")
    private java.time.LocalDateTime lastLogin;

    // Many-to-One relationship with School
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    // Helper method to get full name
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
