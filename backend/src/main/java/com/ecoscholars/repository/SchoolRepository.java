package com.ecoscholars.repository;

import com.ecoscholars.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for School entity
 */
@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    Optional<School> findByNameAndIsActiveTrue(String name);

    List<School> findByCityAndIsActiveTrue(String city);

    List<School> findByStateAndIsActiveTrue(String state);

    @Query("SELECT s FROM School s WHERE s.isActive = true ORDER BY s.totalStudents DESC")
    List<School> findAllActiveSchoolsOrderByStudentCount();

    @Query("SELECT s FROM School s WHERE s.isActive = true ORDER BY s.totalPlants DESC")
    List<School> findAllActiveSchoolsOrderByPlantCount();

    @Query("SELECT COUNT(s) FROM School s WHERE s.isActive = true")
    Long countActiveSchools();

    @Query("SELECT SUM(s.totalStudents) FROM School s WHERE s.isActive = true")
    Long getTotalActiveStudents();

    @Query("SELECT SUM(s.totalPlants) FROM School s WHERE s.isActive = true")
    Long getTotalActivePlants();
}
