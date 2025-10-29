package com.ecoscholars.repository;

import com.ecoscholars.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Achievement entity
 */
@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    List<Achievement> findByStudentIdAndIsActiveTrueOrderByAwardedDateDesc(Long studentId);

    List<Achievement> findByTypeAndIsActiveTrue(String type);

    Long countByStudentIdAndIsActiveTrue(Long studentId);

    Long countByTypeAndIsActiveTrue(String type);
}
