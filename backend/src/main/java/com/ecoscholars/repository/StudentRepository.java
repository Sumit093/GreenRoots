package com.ecoscholars.repository;

import com.ecoscholars.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Student entity
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmailAndIsActiveTrue(String email);

    List<Student> findBySchoolIdAndIsActiveTrue(Long schoolId);

    List<Student> findBySchoolIdAndIsGraduatedFalseAndIsActiveTrue(Long schoolId);

    List<Student> findByGradeAndSchoolIdAndIsActiveTrue(String grade, Long schoolId);

    @Query("SELECT s FROM Student s WHERE s.school.id = :schoolId AND s.isActive = true ORDER BY s.greenPoints DESC")
    List<Student> findBySchoolIdOrderByGreenPointsDesc(@Param("schoolId") Long schoolId);

    @Query("SELECT s FROM Student s WHERE s.isActive = true ORDER BY s.greenPoints DESC")
    List<Student> findAllActiveStudentsOrderByGreenPointsDesc();

    @Query("SELECT COUNT(s) FROM Student s WHERE s.school.id = :schoolId AND s.isActive = true")
    Long countActiveStudentsBySchoolId(@Param("schoolId") Long schoolId);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.school.id = :schoolId AND s.isGraduated = true AND s.isActive = true")
    Long countGraduatedStudentsBySchoolId(@Param("schoolId") Long schoolId);

    @Query("SELECT s FROM Student s WHERE s.assignedPlant IS NULL AND s.isActive = true")
    List<Student> findStudentsWithoutPlants();

    @Query("SELECT s FROM Student s WHERE s.school.id = :schoolId AND s.assignedPlant IS NULL AND s.isActive = true")
    List<Student> findStudentsWithoutPlantsBySchoolId(@Param("schoolId") Long schoolId);
}
