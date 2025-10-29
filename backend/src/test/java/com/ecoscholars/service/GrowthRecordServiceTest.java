package com.ecoscholars.service;

import com.ecoscholars.dto.growthrecord.GrowthRecordRequest;
import com.ecoscholars.dto.growthrecord.GrowthRecordResponse;
import com.ecoscholars.entity.GrowthRecord;
import com.ecoscholars.entity.Plant;
import com.ecoscholars.entity.Student;
import com.ecoscholars.exception.ResourceNotFoundException;
import com.ecoscholars.repository.GrowthRecordRepository;
import com.ecoscholars.repository.PlantRepository;
import com.ecoscholars.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GrowthRecordServiceTest {

    @Mock
    private GrowthRecordRepository growthRecordRepository;
    @Mock
    private PlantRepository plantRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CloudinaryService cloudinaryService;
    @Mock
    private MultipartFile mockImageFile;

    @InjectMocks
    private GrowthRecordService growthRecordService;

    private GrowthRecordRequest request;
    private Plant plant;
    private Student student;
    private GrowthRecord growthRecord;

    @BeforeEach
    void setUp() {
        plant = new Plant();
        plant.setId(1L);
        plant.setName("Test Plant");
        plant.setCurrentHeight(10.0);
        plant.setCurrentWidth(5.0);
        plant.setHealthStatus("HEALTHY");

        student = new Student();
        student.setId(1L);
        student.setFirstName("Test");
        student.setLastName("Student");
        student.setGreenPoints(100);

        growthRecord = new GrowthRecord();
        growthRecord.setId(1L);
        growthRecord.setYear(2023);
        growthRecord.setHeight(10.0);
        growthRecord.setWidth(5.0);
        growthRecord.setHealthStatus("HEALTHY");
        growthRecord.setRecordDate(LocalDate.now());
        growthRecord.setPlant(plant);
        growthRecord.setStudent(student);
        growthRecord.setIsActive(true);
        growthRecord.setCreatedAt(LocalDateTime.now());
        growthRecord.setUpdatedAt(LocalDateTime.now());

        request = new GrowthRecordRequest();
        request.setPlantId(1L);
        request.setStudentId(1L);
        request.setYear(2023);
        request.setHeight(12.0);
        request.setWidth(6.0);
        request.setHealthStatus("HEALTHY");
        request.setRecordDate(LocalDate.now());
        request.setImageFile(mockImageFile);
    }

    @Test
    void createGrowthRecord_successWithImage() throws Exception {
        when(plantRepository.findById(anyLong())).thenReturn(Optional.of(plant));
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(mockImageFile.isEmpty()).thenReturn(false);
        when(mockImageFile.getBytes()).thenReturn("test_image_data".getBytes());
        when(cloudinaryService.uploadFile(any(MultipartFile.class)))
                .thenReturn(Map.of("secure_url", "http://test.com/image.jpg"));
        when(growthRecordRepository.save(any(GrowthRecord.class))).thenReturn(growthRecord);
        when(plantRepository.save(any(Plant.class))).thenReturn(plant);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        GrowthRecordResponse response = growthRecordService.createGrowthRecord(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("http://test.com/image.jpg", response.getPhotoUrl());
        assertEquals(110, student.getGreenPoints()); // +10 points
        verify(growthRecordRepository, times(1)).save(any(GrowthRecord.class));
        verify(plantRepository, times(1)).save(any(Plant.class));
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(cloudinaryService, times(1)).uploadFile(any(MultipartFile.class));
    }

    @Test
    void createGrowthRecord_plantNotFound() {
        when(plantRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> growthRecordService.createGrowthRecord(request));
        verify(growthRecordRepository, never()).save(any(GrowthRecord.class));
    }

    @Test
    void getGrowthRecordById_success() {
        when(growthRecordRepository.findById(anyLong())).thenReturn(Optional.of(growthRecord));

        GrowthRecordResponse response = growthRecordService.getGrowthRecordById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void deleteGrowthRecord_success() {
        when(growthRecordRepository.findById(anyLong())).thenReturn(Optional.of(growthRecord));
        when(growthRecordRepository.save(any(GrowthRecord.class))).thenReturn(growthRecord);

        growthRecordService.deleteGrowthRecord(1L);

        assertFalse(growthRecord.getIsActive());
        verify(growthRecordRepository, times(1)).save(growthRecord);
    }

    @Test
    void verifyGrowthRecord_success() {
        when(growthRecordRepository.findById(anyLong())).thenReturn(Optional.of(growthRecord));
        when(growthRecordRepository.save(any(GrowthRecord.class))).thenReturn(growthRecord);

        GrowthRecordResponse response = growthRecordService.verifyGrowthRecord(1L, "Admin User");

        assertTrue(response.getIsVerified());
        assertEquals("Admin User", response.getVerifiedBy());
        assertNotNull(response.getVerificationDate());
        verify(growthRecordRepository, times(1)).save(growthRecord);
    }

    @Test
    void getGrowthRecordsByPlant_success() {
        when(plantRepository.findById(anyLong())).thenReturn(Optional.of(plant));
        when(growthRecordRepository.findByPlantIdAndIsActiveTrueOrderByRecordDateDesc(anyLong()))
                .thenReturn(Arrays.asList(growthRecord));

        List<GrowthRecordResponse> responses = growthRecordService.getGrowthRecordsByPlant(1L);

        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
    }

    @Test
    void updateGrowthRecord_success() throws Exception {
        GrowthRecord updatedRecord = new GrowthRecord();
        updatedRecord.setId(1L);
        updatedRecord.setYear(2023);
        updatedRecord.setHeight(15.0);
        updatedRecord.setWidth(7.0);
        updatedRecord.setHealthStatus("NEEDS_CARE");
        updatedRecord.setRecordDate(LocalDate.now());
        updatedRecord.setPlant(plant);
        updatedRecord.setStudent(student);
        updatedRecord.setIsActive(true);
        updatedRecord.setCreatedAt(LocalDateTime.now());
        updatedRecord.setUpdatedAt(LocalDateTime.now());

        GrowthRecordRequest updateRequest = new GrowthRecordRequest();
        updateRequest.setHeight(15.0);
        updateRequest.setWidth(7.0);
        updateRequest.setHealthStatus("NEEDS_CARE");

        when(growthRecordRepository.findById(anyLong())).thenReturn(Optional.of(growthRecord));
        when(growthRecordRepository.save(any(GrowthRecord.class))).thenReturn(updatedRecord);
        when(plantRepository.save(any(Plant.class))).thenReturn(plant);

        GrowthRecordResponse response = growthRecordService.updateGrowthRecord(1L, updateRequest);

        assertNotNull(response);
        assertEquals(15.0, response.getHeight());
        assertEquals("NEEDS_CARE", response.getHealthStatus());
        verify(growthRecordRepository, times(1)).save(any(GrowthRecord.class));
        verify(plantRepository, times(1)).save(any(Plant.class));
    }
}
