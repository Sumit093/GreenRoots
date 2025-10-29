package com.ecoscholars.service;

import com.ecoscholars.dto.student.StudentRegistrationRequest;
import com.ecoscholars.dto.student.StudentResponse;
import com.ecoscholars.entity.School;
import com.ecoscholars.entity.Student;
import com.ecoscholars.entity.User;
import com.ecoscholars.entity.UserRole;
import com.ecoscholars.exception.ResourceNotFoundException;
import com.ecoscholars.repository.PlantRepository;
import com.ecoscholars.repository.SchoolRepository;
import com.ecoscholars.repository.StudentRepository;
import com.ecoscholars.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PlantRepository plantRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentService studentService;

    private StudentRegistrationRequest registrationRequest;
    private School school;
    private Student student;

    @BeforeEach
    void setUp() {
        registrationRequest = new StudentRegistrationRequest();
        registrationRequest.setFirstName("Test");
        registrationRequest.setLastName("Student");
        registrationRequest.setEmail("test.student@example.com");
        registrationRequest.setPassword("password");
        registrationRequest.setDateOfBirth(LocalDate.of(2010, 1, 1));
        registrationRequest.setGrade("5th");
        registrationRequest.setRollNumber("TS001");
        registrationRequest.setParentName("Parent Name");
        registrationRequest.setEnrollmentDate(LocalDate.of(2020, 9, 1));
        registrationRequest.setSchoolId(1L);

        school = new School();
        school.setId(1L);
        school.setName("Test School");
        school.setTotalStudents(0);

        student = new Student();
        student.setId(1L);
        student.setFirstName("Test");
        student.setLastName("Student");
        student.setEmail("test.student@example.com");
        student.setPassword("encodedPassword");
        student.setDateOfBirth(LocalDate.of(2010, 1, 1));
        student.setGrade("5th");
        student.setRollNumber("TS001");
        student.setParentName("Parent Name");
        student.setEnrollmentDate(LocalDate.of(2020, 9, 1));
        student.setIsActive(true);
        student.setSchool(school);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void registerStudent_success() {
        when(userRepository.existsByEmailAndIsActiveTrue(anyString())).thenReturn(false);
        when(schoolRepository.findById(anyLong())).thenReturn(Optional.of(school));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(schoolRepository.save(any(School.class))).thenReturn(school);

        StudentResponse response = studentService.registerStudent(registrationRequest);

        assertNotNull(response);
        assertEquals("Test", response.getFirstName());
        assertEquals(1L, response.getSchoolId());
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(userRepository, times(1)).save(any(User.class));
        verify(schoolRepository, times(1)).save(any(School.class));
        assertEquals(1, school.getTotalStudents());
    }

    @Test
    void registerStudent_emailAlreadyExists() {
        when(userRepository.existsByEmailAndIsActiveTrue(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> studentService.registerStudent(registrationRequest));
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void getStudentById_success() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        StudentResponse response = studentService.getStudentById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test", response.getFirstName());
    }

    @Test
    void getStudentById_notFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById(1L));
    }

    @Test
    void deleteStudent_success() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(userRepository.findByStudentId(anyLong())).thenReturn(Optional.of(new User()));
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(schoolRepository.save(any(School.class))).thenReturn(school);

        studentService.deleteStudent(1L);

        assertFalse(student.getIsActive());
        verify(studentRepository, times(1)).save(student);
        verify(userRepository, times(1)).findByStudentId(1L);
        verify(schoolRepository, times(1)).save(school);
    }

    // More tests for update, getAll, getBySchool etc.
}
