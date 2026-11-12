
package com.placement.dto;

import com.placement.entity.Application;

import java.time.LocalDateTime;

public class ApplicationResponse {

    private Long id;
    private Long studentId;
    private String studentName;
    private String registrationNumber;
    private Long driveId;
    private String companyName;
    private String jobRole;
    private LocalDateTime appliedAt;
    private String status;

    public ApplicationResponse(Application application) {

        this.id = application.getId();

        this.studentId = application.getStudent().getId();
        this.studentName = application.getStudent()
                .getUser().getFullName();

        this.registrationNumber = application.getStudent()
                .getRegistrationNumber();

        this.driveId = application.getPlacementDrive().getId();

        this.companyName = application.getPlacementDrive()
                .getCompany().getCompanyName();

        this.jobRole = application.getPlacementDrive()
                .getJobRole();

        this.appliedAt = application.getAppliedAt();
        this.status = application.getStatus();
    }

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Long getDriveId() {
        return driveId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobRole() {
        return jobRole;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public String getStatus() {
        return status;
    }
}

