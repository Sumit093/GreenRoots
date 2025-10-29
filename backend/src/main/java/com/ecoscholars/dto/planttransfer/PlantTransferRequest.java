package com.ecoscholars.dto.planttransfer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlantTransferRequest {

    @NotNull(message = "Plant ID is required")
    private Long plantId;

    @NotNull(message = "Previous student ID is required")
    private Long previousStudentId;

    private Long newStudentId;

    @NotNull(message = "Transfer date is required")
    @PastOrPresent(message = "Transfer date cannot be in the future")
    private LocalDate transferDate;

    @NotBlank(message = "Reason for transfer is required")
    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    private String reason; // STUDENT_TRANSFER, STUDENT_GRADUATION, STUDENT_LEAVE

    @Size(max = 1000, message = "Transfer notes cannot exceed 1000 characters")
    private String transferNotes;

    @Size(max = 255, message = "Plant condition cannot exceed 255 characters")
    private String plantCondition;

    @Size(max = 1000, message = "Handover notes cannot exceed 1000 characters")
    private String handoverNotes;

    @Size(max = 255, message = "Initiated by cannot exceed 255 characters")
    private String initiatedBy;

    @Size(max = 255, message = "Approved by cannot exceed 255 characters")
    private String approvedBy;

    private LocalDate approvalDate;

    private Boolean isCompleted;
    private LocalDate completedDate;
}
