package com.ecoscholars.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * School entity representing educational institutions
 */
@Data
@Entity
@Table(name = "schools")
@EqualsAndHashCode(callSuper = true)
public class School extends BaseEntity {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "pin_code", length = 10)
    private String pinCode;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "website", length = 255)
    private String website;

    @Column(name = "principal_name", length = 255)
    private String principalName;

    @Column(name = "registration_number", length = 100)
    private String registrationNumber;

    @Column(name = "total_students")
    private Integer totalStudents = 0;

    @Column(name = "total_plants")
    private Integer totalPlants = 0;

    // One-to-Many relationship with students
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;

    // One-to-Many relationship with plants
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Plant> plants;

    // One-to-Many relationship with school admins
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SchoolAdmin> schoolAdmins;
}

