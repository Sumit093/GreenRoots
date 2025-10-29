package com.ecoscholars.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Plant Transfer entity for tracking plant reassignments
 */
@Data
@Entity
@Table(name = "plant_transfers")
@EqualsAndHashCode(callSuper = true)
public class PlantTransfer extends BaseEntity {

    @Column(name = "transfer_date", nullable = false)
    private LocalDate transferDate;

    @Column(name = "reason", length = 500)
    private String reason; // STUDENT_TRANSFER, STUDENT_GRADUATION, STUDENT_LEAVE

    @Column(name = "transfer_notes", length = 1000)
    private String transferNotes;

    @Column(name = "plant_condition", length = 255)
    private String plantCondition;

    @Column(name = "handover_notes", length = 1000)
    private String handoverNotes;

    @Column(name = "is_completed")
    private Boolean isCompleted = false;

    @Column(name = "completed_date")
    private LocalDate completedDate;

    @Column(name = "initiated_by", length = 255)
    private String initiatedBy;

    @Column(name = "approved_by", length = 255)
    private String approvedBy;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    // Many-to-One relationship with Plant
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    // Many-to-One relationship with Previous Student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_student_id", nullable = false)
    private Student previousStudent;

    // Many-to-One relationship with New Student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_student_id")
    private Student newStudent;
}
