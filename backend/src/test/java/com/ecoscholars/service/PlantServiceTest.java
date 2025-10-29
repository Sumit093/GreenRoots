package com.ecoscholars.service;

import com.ecoscholars.dto.plant.PlantRegistrationRequest;
import com.ecoscholars.dto.plant.PlantResponse;
import com.ecoscholars.dto.plant.PlantUpdateRequest;
import com.ecoscholars.entity.Plant;
import com.ecoscholars.entity.School;
import com.ecoscholars.entity.Student;
import com.ecoscholars.exception.ResourceNotFoundException;
import com.ecoscholars.repository.PlantRepository;
import com.ecoscholars.repository.SchoolRepository;
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
public class PlantServiceTest {

    @Mock
    private PlantRepository plantRepository;
    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private PlantService plantService;

    private PlantRegistrationRequest registrationRequest;
    private PlantUpdateRequest updateRequest;
    private School school;
    private Student student;
    private Plant plant;

    @BeforeEach
    void setUp() {
        school = new School();
        school.setId(1L);
        school.setName("Test School");
        school.setTotalPlants(0);

        student = new Student();
        student.setId(10L);
        student.setFirstName("Plant");
        student.setLastName("Owner");

        plant = new Plant();
        plant.setId(100L);
        plant.setName("Test Plant");
        plant.setSpecies("Test Species");
        plant.setDateOfPlantation(LocalDate.now());
        plant.setIsActive(true);
        plant.setSchool(school);
        plant.setAssignedStudent(student);
        plant.setCreatedAt(LocalDateTime.now());
        plant.setUpdatedAt(LocalDateTime.now());

        registrationRequest = new PlantRegistrationRequest();
        registrationRequest.setName("New Plant");
        registrationRequest.setSpecies("New Species");
        registrationRequest.setDateOfPlantation(LocalDate.now());
        registrationRequest.setSchoolId(1L);
        registrationRequest.setAssignedStudentId(10L);

        updateRequest = new PlantUpdateRequest();
        updateRequest.setName("Updated Plant Name");
        updateRequest.setHealthStatus("NEEDS_CARE");
    }

    @Test
    void registerPlant_success() {
        when(schoolRepository.findById(anyLong())).thenReturn(Optional.of(school));
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(plantRepository.save(any(Plant.class))).thenReturn(plant);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(schoolRepository.save(any(School.class))).thenReturn(school);

        PlantResponse response = plantService.registerPlant(registrationRequest);

        assertNotNull(response);
        assertEquals("Test Plant", response.getName());
        assertEquals(100L, response.getId());
        verify(plantRepository, times(1)).save(any(Plant.class));
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(schoolRepository, times(1)).save(any(School.class));
        assertEquals(1, school.getTotalPlants());
        assertEquals(plant, student.getAssignedPlant());
    }

    @Test
    void registerPlant_schoolNotFound() {
        when(schoolRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> plantService.registerPlant(registrationRequest));
        verify(plantRepository, never()).save(any(Plant.class));
    }

    @Test
    void getPlantById_success() {
        when(plantRepository.findById(anyLong())).thenReturn(Optional.of(plant));

        PlantResponse response = plantService.getPlantById(100L);

        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals("Test Plant", response.getName());
    }

    @Test
    void getPlantById_notFound() {
        when(plantRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> plantService.getPlantById(100L));
    }

    @Test
    void updatePlant_success() {
        when(plantRepository.findById(anyLong())).thenReturn(Optional.of(plant));
        when(plantRepository.save(any(Plant.class))).thenReturn(plant);

        PlantResponse response = plantService.updatePlant(100L, updateRequest);

        assertNotNull(response);
        assertEquals("Updated Plant Name", response.getName());
        assertEquals("NEEDS_CARE", response.getHealthStatus());
        verify(plantRepository, times(1)).save(plant);
    }

    @Test
    void deletePlant_success() {
        when(plantRepository.findById(anyLong())).thenReturn(Optional.of(plant));
        when(plantRepository.save(any(Plant.class))).thenReturn(plant);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(schoolRepository.save(any(School.class))).thenReturn(school);

        plantService.deletePlant(100L);

        assertFalse(plant.getIsActive());
        assertNull(student.getAssignedPlant());
        verify(plantRepository, times(1)).save(plant);
        verify(studentRepository, times(1)).save(student);
        verify(schoolRepository, times(1)).save(school);
    }

    @Test
    void getPlantsBySchool_success() {
        when(schoolRepository.findById(anyLong())).thenReturn(Optional.of(school));
        when(plantRepository.findBySchoolIdAndIsActiveTrue(anyLong()))
                .thenReturn(Arrays.asList(plant));

        List<PlantResponse> responses = plantService.getPlantsBySchool(1L);

        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals("Test Plant", responses.get(0).getName());
    }
}
