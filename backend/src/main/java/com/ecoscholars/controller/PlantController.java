package com.ecoscholars.controller;

import com.ecoscholars.dto.plant.PlantRegistrationRequest;
import com.ecoscholars.dto.plant.PlantResponse;
import com.ecoscholars.dto.plant.PlantUpdateRequest;
import com.ecoscholars.service.PlantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<PlantResponse> registerPlant(@Valid @RequestBody PlantRegistrationRequest request) {
        PlantResponse newPlant = plantService.registerPlant(request);
        return new ResponseEntity<>(newPlant, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<List<PlantResponse>> getAllPlants() {
        List<PlantResponse> plants = plantService.getAllPlants();
        return ResponseEntity.ok(plants);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<PlantResponse> getPlantById(@PathVariable Long id) {
        PlantResponse plant = plantService.getPlantById(id);
        return ResponseEntity.ok(plant);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<PlantResponse> updatePlant(@PathVariable Long id, @Valid @RequestBody PlantUpdateRequest request) {
        PlantResponse updatedPlant = plantService.updatePlant(id, request);
        return ResponseEntity.ok(updatedPlant);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
        plantService.deletePlant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/school/{schoolId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<PlantResponse>> getPlantsBySchool(@PathVariable Long schoolId) {
        List<PlantResponse> plants = plantService.getPlantsBySchool(schoolId);
        return ResponseEntity.ok(plants);
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN', 'STUDENT')")
    public ResponseEntity<List<PlantResponse>> getPlantsByStudent(@PathVariable Long studentId) {
        List<PlantResponse> plants = plantService.getPlantsByStudent(studentId);
        return ResponseEntity.ok(plants);
    }
}
