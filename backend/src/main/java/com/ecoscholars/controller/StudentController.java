package com.ecoscholars.controller;

import com.ecoscholars.dto.student.StudentRegistrationRequest;
import com.ecoscholars.dto.student.StudentResponse;
import com.ecoscholars.dto.student.StudentUpdateRequest;
import com.ecoscholars.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StudentResponse> registerStudent(@Valid @RequestBody StudentRegistrationRequest request) {
        StudentResponse newStudent = studentService.registerStudent(request);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
        StudentResponse student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentUpdateRequest request) {
        StudentResponse updatedStudent = studentService.updateStudent(id, request);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/school/{schoolId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<StudentResponse>> getStudentsBySchool(@PathVariable Long schoolId) {
        List<StudentResponse> students = studentService.getStudentsBySchool(schoolId);
        return ResponseEntity.ok(students);
    }
}
