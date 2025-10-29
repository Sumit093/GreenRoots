package com.ecoscholars.controller;

import com.ecoscholars.dto.growthrecord.GrowthRecordRequest;
import com.ecoscholars.dto.growthrecord.GrowthRecordResponse;
import com.ecoscholars.service.GrowthRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/growth-records")
@RequiredArgsConstructor
public class GrowthRecordController {

    private final GrowthRecordService growthRecordService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<GrowthRecordResponse> createGrowthRecord(@Valid @ModelAttribute GrowthRecordRequest request) {
        GrowthRecordResponse newRecord = growthRecordService.createGrowthRecord(request);
        return new ResponseEntity<>(newRecord, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<List<GrowthRecordResponse>> getAllGrowthRecords() {
        List<GrowthRecordResponse> records = growthRecordService.getAllGrowthRecords();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<GrowthRecordResponse> getGrowthRecordById(@PathVariable Long id) {
        GrowthRecordResponse record = growthRecordService.getGrowthRecordById(id);
        return ResponseEntity.ok(record);
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<GrowthRecordResponse> updateGrowthRecord(@PathVariable Long id, @Valid @ModelAttribute GrowthRecordRequest request) {
        GrowthRecordResponse updatedRecord = growthRecordService.updateGrowthRecord(id, request);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> deleteGrowthRecord(@PathVariable Long id) {
        growthRecordService.deleteGrowthRecord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/plant/{plantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<List<GrowthRecordResponse>> getGrowthRecordsByPlant(@PathVariable Long plantId) {
        List<GrowthRecordResponse> records = growthRecordService.getGrowthRecordsByPlant(plantId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<List<GrowthRecordResponse>> getGrowthRecordsByStudent(@PathVariable Long studentId) {
        List<GrowthRecordResponse> records = growthRecordService.getGrowthRecordsByStudent(studentId);
        return ResponseEntity.ok(records);
    }

    @PutMapping("/{id}/verify")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<GrowthRecordResponse> verifyGrowthRecord(@PathVariable Long id, @RequestParam String verifiedBy) {
        GrowthRecordResponse verifiedRecord = growthRecordService.verifyGrowthRecord(id, verifiedBy);
        return ResponseEntity.ok(verifiedRecord);
    }
}
