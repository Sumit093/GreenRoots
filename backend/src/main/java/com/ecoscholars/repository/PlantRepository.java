package com.ecoscholars.repository;

import com.ecoscholars.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Plant entity
 */
@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {

    List<Plant> findBySchoolIdAndIsActiveTrue(Long schoolId);

    List<Plant> findByAssignedStudentIdAndIsActiveTrue(Long studentId);

    List<Plant> findBySpeciesAndIsActiveTrue(String species);

    @Query("SELECT p FROM Plant p WHERE p.assignedStudent IS NULL AND p.isActive = true")
    List<Plant> findUnassignedPlants();

    @Query("SELECT p FROM Plant p WHERE p.school.id = :schoolId AND p.assignedStudent IS NULL AND p.isActive = true")
    List<Plant> findUnassignedPlantsBySchoolId(@Param("schoolId") Long schoolId);

    @Query("SELECT p FROM Plant p WHERE p.dateOfPlantation BETWEEN :startDate AND :endDate AND p.isActive = true")
    List<Plant> findPlantsPlantedBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Plant p WHERE p.healthStatus = :healthStatus AND p.isActive = true")
    List<Plant> findByHealthStatus(@Param("healthStatus") String healthStatus);

    @Query("SELECT COUNT(p) FROM Plant p WHERE p.school.id = :schoolId AND p.isActive = true")
    Long countActivePlantsBySchoolId(@Param("schoolId") Long schoolId);

    @Query("SELECT COUNT(p) FROM Plant p WHERE p.isActive = true")
    Long countAllActivePlants();

    @Query("SELECT p.species, COUNT(p) FROM Plant p WHERE p.isActive = true GROUP BY p.species ORDER BY COUNT(p) DESC")
    List<Object[]> getPlantSpeciesDistribution();

    @Query("SELECT p FROM Plant p WHERE p.isTransferred = true AND p.isActive = true")
    List<Plant> findTransferredPlants();
}
