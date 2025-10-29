package com.ecoscholars.controller;

import com.ecoscholars.dto.planttransfer.PlantTransferRequest;
import com.ecoscholars.dto.planttransfer.PlantTransferResponse;
import com.ecoscholars.service.PlantTransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plant-transfers")
@RequiredArgsConstructor
public class PlantTransferController {

    private final PlantTransferService plantTransferService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<PlantTransferResponse> initiatePlantTransfer(@Valid @RequestBody PlantTransferRequest request) {
        PlantTransferResponse newTransfer = plantTransferService.initiatePlantTransfer(request);
        return new ResponseEntity<>(newTransfer, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<PlantTransferResponse>> getAllPlantTransfers() {
        List<PlantTransferResponse> transfers = plantTransferService.getAllPlantTransfers();
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<PlantTransferResponse> getPlantTransferById(@PathVariable Long id) {
        PlantTransferResponse transfer = plantTransferService.getPlantTransferById(id);
        return ResponseEntity.ok(transfer);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<PlantTransferResponse> updatePlantTransfer(@PathVariable Long id, @Valid @RequestBody PlantTransferRequest request) {
        PlantTransferResponse updatedTransfer = plantTransferService.updatePlantTransfer(id, request);
        return ResponseEntity.ok(updatedTransfer);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> deletePlantTransfer(@PathVariable Long id) {
        plantTransferService.deletePlantTransfer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/plant/{plantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<PlantTransferResponse>> getPlantTransfersByPlant(@PathVariable Long plantId) {
        List<PlantTransferResponse> transfers = plantTransferService.getPlantTransfersByPlant(plantId);
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/previous-student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<PlantTransferResponse>> getPlantTransfersByPreviousStudent(@PathVariable Long studentId) {
        List<PlantTransferResponse> transfers = plantTransferService.getPlantTransfersByPreviousStudent(studentId);
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/new-student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL_ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<PlantTransferResponse>> getPlantTransfersByNewStudent(@PathVariable Long studentId) {
        List<PlantTransferResponse> transfers = plantTransferService.getPlantTransfersByNewStudent(studentId);
        return ResponseEntity.ok(transfers);
    }
}
