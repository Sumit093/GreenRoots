package com.ecoscholars.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * System Admin entity for super administrators
 */
@Data
@Entity
@Table(name = "system_admins")
@EqualsAndHashCode(callSuper = true)
public class SystemAdmin extends BaseEntity {

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

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified = false;

    @Column(name = "last_login")
    private java.time.LocalDateTime lastLogin;

    @Column(name = "permissions", length = 1000)
    private String permissions; // JSON string of permissions

    // Helper method to get full name
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
