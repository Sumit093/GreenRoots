package com.ecoscholars.dto.planttransfer;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PlantTransferResponse {
    private Long id;
    private Long plantId;
    private String plantName;
    private Long previousStudentId;
    private String previousStudentName;
    private Long newStudentId;
    private String newStudentName;
    private LocalDate transferDate;
    private String reason;
    private String transferNotes;
    private String plantCondition;
    private String handoverNotes;
    private Boolean isCompleted;
    private LocalDate completedDate;
    private String initiatedBy;
    private String approvedBy;
    private LocalDate approvalDate;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
