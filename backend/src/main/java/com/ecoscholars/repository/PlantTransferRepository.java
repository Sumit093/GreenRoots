package com.ecoscholars.repository;

import com.ecoscholars.entity.PlantTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for PlantTransfer entity
 */
@Repository
public interface PlantTransferRepository extends JpaRepository<PlantTransfer, Long> {

    List<PlantTransfer> findByPlantIdAndIsActiveTrueOrderByTransferDateDesc(Long plantId);

    List<PlantTransfer> findByPreviousStudentIdAndIsActiveTrueOrderByTransferDateDesc(Long studentId);

    List<PlantTransfer> findByNewStudentIdAndIsActiveTrueOrderByTransferDateDesc(Long studentId);

    List<PlantTransfer> findByIsCompletedFalseAndIsActiveTrue();

    Long countByPlantIdAndIsActiveTrue(Long plantId);

    Long countByPreviousStudentIdAndIsActiveTrue(Long studentId);

    Long countByNewStudentIdAndIsActiveTrue(Long studentId);

    Long countByIsCompletedFalseAndIsActiveTrue();
}
