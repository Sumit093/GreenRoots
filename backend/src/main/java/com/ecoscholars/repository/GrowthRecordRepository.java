package com.ecoscholars.repository;

import com.ecoscholars.entity.GrowthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for GrowthRecord entity
 */
@Repository
public interface GrowthRecordRepository extends JpaRepository<GrowthRecord, Long> {

    List<GrowthRecord> findByPlantIdAndIsActiveTrueOrderByRecordDateDesc(Long plantId);

    List<GrowthRecord> findByStudentIdAndIsActiveTrueOrderByRecordDateDesc(Long studentId);

    List<GrowthRecord> findByPlantIdAndYearAndIsActiveTrue(Long plantId, Integer year);

    List<GrowthRecord> findByStudentIdAndYearAndIsActiveTrue(Long studentId, Integer year);

    List<GrowthRecord> findByIsVerifiedFalseAndIsActiveTrue();

    Long countByPlantIdAndIsActiveTrue(Long plantId);

    Long countByStudentIdAndIsActiveTrue(Long studentId);

    Long countByIsVerifiedFalseAndIsActiveTrue();
}
