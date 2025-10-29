package com.ecoscholars.service;

import com.ecoscholars.dto.student.StudentRegistrationRequest;
import com.ecoscholars.dto.student.StudentResponse;
import com.ecoscholars.dto.student.StudentUpdateRequest;
import com.ecoscholars.entity.Plant;
import com.ecoscholars.entity.School;
import com.ecoscholars.entity.Student;
import com.ecoscholars.entity.User;
import com.ecoscholars.entity.UserRole;
import com.ecoscholars.exception.ResourceNotFoundException;
import com.ecoscholars.repository.PlantRepository;
import com.ecoscholars.repository.SchoolRepository;
import com.ecoscholars.repository.StudentRepository;
import com.ecoscholars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final PlantRepository plantRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public StudentResponse registerStudent(StudentRegistrationRequest request) {
        if (userRepository.existsByEmailAndIsActiveTrue(request.getEmail())) {
            throw new IllegalArgumentException("Email address already in use.");
        }

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new ResourceNotFoundException("School", "id", request.getSchoolId()));

        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGrade(request.getGrade());
        student.setRollNumber(request.getRollNumber());
        student.setPhone(request.getPhone());
        student.setParentName(request.getParentName());
        student.setParentPhone(request.getParentPhone());
        student.setParentEmail(request.getParentEmail());
        student.setAddress(request.getAddress());
        student.setEnrollmentDate(request.getEnrollmentDate());
        student.setIsEmailVerified(false);
        student.setIsActive(true);
        student.setSchool(school);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        Student savedStudent = studentRepository.save(student);

        // Create a user entry for authentication
        User user = new User();
        user.setUsername(generateUsername(savedStudent.getFirstName(), savedStudent.getLastName()));
        user.setEmail(savedStudent.getEmail());
        user.setPassword(savedStudent.getPassword());
        user.setFirstName(savedStudent.getFirstName());
        user.setLastName(savedStudent.getLastName());
        user.setRoles(Collections.singleton(UserRole.STUDENT));
        user.setIsActive(true);
        user.setIsEmailVerified(false);
        user.setStudentId(savedStudent.getId());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        school.setTotalStudents(school.getTotalStudents() + 1);
        schoolRepository.save(school);

        return convertToDto(savedStudent);
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        return convertToDto(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsBySchool(Long schoolId) {
        schoolRepository.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School", "id", schoolId));

        return studentRepository.findBySchoolIdAndIsActiveTrue(schoolId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public StudentResponse updateStudent(Long id, StudentUpdateRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        // Update fields if present in request
        if (request.getFirstName() != null) student.setFirstName(request.getFirstName());
        if (request.getLastName() != null) student.setLastName(request.getLastName());
        if (request.getEmail() != null) {
            if (userRepository.existsByEmailAndIsActiveTrue(request.getEmail()) && !student.getEmail().equals(request.getEmail())) {
                throw new IllegalArgumentException("Email address already in use.");
            }
            student.setEmail(request.getEmail());
        }
        if (request.getDateOfBirth() != null) student.setDateOfBirth(request.getDateOfBirth());
        if (request.getGrade() != null) student.setGrade(request.getGrade());
        if (request.getRollNumber() != null) student.setRollNumber(request.getRollNumber());
        if (request.getPhone() != null) student.setPhone(request.getPhone());
        if (request.getParentName() != null) student.setParentName(request.getParentName());
        if (request.getParentPhone() != null) student.setParentPhone(request.getParentPhone());
        if (request.getParentEmail() != null) student.setParentEmail(request.getParentEmail());
        if (request.getAddress() != null) student.setAddress(request.getAddress());
        if (request.getEnrollmentDate() != null) student.setEnrollmentDate(request.getEnrollmentDate());
        if (request.getIsGraduated() != null) student.setIsGraduated(request.getIsGraduated());
        if (request.getGraduationDate() != null) student.setGraduationDate(request.getGraduationDate());
        if (request.getGreenPoints() != null) student.setGreenPoints(request.getGreenPoints());
        if (request.getIsActive() != null) student.setIsActive(request.getIsActive());

        if (request.getSchoolId() != null && !request.getSchoolId().equals(student.getSchool().getId())) {
            School newSchool = schoolRepository.findById(request.getSchoolId())
                    .orElseThrow(() -> new ResourceNotFoundException("School", "id", request.getSchoolId()));
            student.setSchool(newSchool);
        }
        student.setUpdatedAt(LocalDateTime.now());

        Student updatedStudent = studentRepository.save(student);

        // Update associated user entity
        userRepository.findByStudentId(updatedStudent.getId()).ifPresent(user -> {
            user.setEmail(updatedStudent.getEmail());
            user.setFirstName(updatedStudent.getFirstName());
            user.setLastName(updatedStudent.getLastName());
            user.setIsActive(updatedStudent.getIsActive());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        });

        return convertToDto(updatedStudent);
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        // Deactivate student
        student.setIsActive(false);
        student.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(student);

        // Deactivate associated user
        userRepository.findByStudentId(id).ifPresent(user -> {
            user.setIsActive(false);
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        });

        // Unassign plant if any
        if (student.getAssignedPlant() != null) {
            Plant plant = student.getAssignedPlant();
            plant.setAssignedStudent(null);
            plantRepository.save(plant);
        }

        // Decrement student count in school
        School school = student.getSchool();
        if (school != null) {
            school.setTotalStudents(school.getTotalStudents() - 1);
            schoolRepository.save(school);
        }
    }

    private StudentResponse convertToDto(Student student) {
        StudentResponse dto = new StudentResponse();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setGrade(student.getGrade());
        dto.setRollNumber(student.getRollNumber());
        dto.setPhone(student.getPhone());
        dto.setParentName(student.getParentName());
        dto.setParentPhone(student.getParentPhone());
        dto.setParentEmail(student.getParentEmail());
        dto.setAddress(student.getAddress());
        dto.setEnrollmentDate(student.getEnrollmentDate());
        dto.setIsGraduated(student.getIsGraduated());
        dto.setGraduationDate(student.getGraduationDate());
        dto.setGreenPoints(student.getGreenPoints());
        dto.setIsEmailVerified(student.getIsEmailVerified());
        dto.setIsActive(student.getIsActive());
        dto.setCreatedAt(student.getCreatedAt());
        dto.setUpdatedAt(student.getUpdatedAt());
        if (student.getSchool() != null) {
            dto.setSchoolId(student.getSchool().getId());
            dto.setSchoolName(student.getSchool().getName());
        }
        if (student.getAssignedPlant() != null) {
            dto.setAssignedPlantId(student.getAssignedPlant().getId());
            dto.setAssignedPlantName(student.getAssignedPlant().getName());
        }
        return dto;
    }

    private String generateUsername(String firstName, String lastName) {
        String baseUsername = (firstName.toLowerCase() + "." + lastName.toLowerCase()).replace(" ", "");
        String username = baseUsername;
        int counter = 1;
        while (userRepository.existsByUsername(username)) {
            username = baseUsername + counter++;
        }
        return username;
    }
}
