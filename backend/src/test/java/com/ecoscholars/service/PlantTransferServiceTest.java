package com.ecoscholars.service;

import com.ecoscholars.dto.planttransfer.PlantTransferRequest;
import com.ecoscholars.dto.planttransfer.PlantTransferResponse;
import com.ecoscholars.entity.Plant;
import com.ecoscholars.entity.PlantTransfer;
import com.ecoscholars.entity.Student;
import com.ecoscholars.exception.ResourceNotFoundException;
import com.ecoscholars.repository.PlantRepository;
import com.ecoscholars.repository.PlantTransferRepository;
import com.ecoscholars.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlantTransferServiceTest {

    @Mock
    private PlantTransferRepository plantTransferRepository;
    @Mock
    private PlantRepository plantRepository;
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private PlantTransferService plantTransferService;

    private PlantTransferRequest request;
    private Plant plant;
    private Student previousStudent;
    private Student newStudent;
    private PlantTransfer plantTransfer;

    @BeforeEach
    void setUp() {
        previousStudent = new Student();
        previousStudent.setId(1L);
        previousStudent.setFirstName("Old");
        previousStudent.setLastName("Student");

        newStudent = new Student();
        newStudent.setId(2L);
        newStudent.setFirstName("New");
        newStudent.setLastName("Student");

        plant = new Plant();
        plant.setId(1L);
        plant.setName("Test Plant");
        plant.setAssignedStudent(previousStudent);

        plantTransfer = new PlantTransfer();
        plantTransfer.setId(1L);
        plantTransfer.setPlant(plant);
        plantTransfer.setPreviousStudent(previousStudent);
        plantTransfer.setNewStudent(newStudent);
        plantTransfer.setTransferDate(LocalDate.now());
        plantTransfer.setReason("STUDENT_TRANSFER");
        plantTransfer.setIsActive(true);
        plantTransfer.setCreatedAt(LocalDateTime.now());
        plantTransfer.setUpdatedAt(LocalDateTime.now());

        request = new PlantTransferRequest();
        request.setPlantId(1L);
        request.setPreviousStudentId(1L);
        request.setNewStudentId(2L);
        request.setTransferDate(LocalDate.now());
        request.setReason("STUDENT_TRANSFER");
        request.setInitiatedBy("Admin");
    }

    @Test
    void initiatePlantTransfer_success() {
        when(plantRepository.findById(anyLong())).thenReturn(Optional.of(plant));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(previousStudent));
        when(studentRepository.findById(2L)).thenReturn(Optional.of(newStudent));
        when(plantTransferRepository.save(any(PlantTransfer.class))).thenReturn(plantTransfer);
        when(plantRepository.save(any(Plant.class))).thenReturn(plant);
        when(studentRepository.save(any(Student.class)))
                .thenReturn(previousStudent)
                .thenReturn(newStudent);

        PlantTransferResponse response = plantTransferService.initiatePlantTransfer(request);

        assertNotNull(response);
        assertEquals(1L, response.getPlantId());
        assertEquals(2L, response.getNewStudentId());
        assertTrue(plant.getIsTransferred());
        assertNull(previousStudent.getAssignedPlant());
        assertEquals(plant, newStudent.getAssignedPlant());
        verify(plantTransferRepository, times(1)).save(any(PlantTransfer.class));
        verify(plantRepository, times(1)).save(any(Plant.class));
        verify(studentRepository, times(2)).save(any(Student.class));
    }

    @Test
    void initiatePlantTransfer_plantNotFound() {
        when(plantRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> plantTransferService.initiatePlantTransfer(request));
        verify(plantTransferRepository, never()).save(any(PlantTransfer.class));
    }

    @Test
    void getPlantTransferById_success() {
        when(plantTransferRepository.findById(anyLong())).thenReturn(Optional.of(plantTransfer));

        PlantTransferResponse response = plantTransferService.getPlantTransferById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void deletePlantTransfer_success() {
        when(plantTransferRepository.findById(anyLong())).thenReturn(Optional.of(plantTransfer));
        when(plantTransferRepository.save(any(PlantTransfer.class))).thenReturn(plantTransfer);

        plantTransferService.deletePlantTransfer(1L);

        assertFalse(plantTransfer.getIsActive());
        verify(plantTransferRepository, times(1)).save(plantTransfer);
    }

    @Test
    void getPlantTransfersByPlant_success() {
        when(plantRepository.findById(anyLong())).thenReturn(Optional.of(plant));
        when(plantTransferRepository.findByPlantIdAndIsActiveTrueOrderByTransferDateDesc(anyLong()))
                .thenReturn(Arrays.asList(plantTransfer));

        List<PlantTransferResponse> responses = plantTransferService.getPlantTransfersByPlant(1L);

        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
    }
}
