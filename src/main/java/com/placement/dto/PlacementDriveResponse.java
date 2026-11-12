
package com.placement.dto;

import com.placement.entity.PlacementDrive;
import java.time.LocalDate;

public class PlacementDriveResponse {

    private Long id;
    private Long companyId;
    private String companyName;
    private String jobRole;
    private String jobDescription;
    private Double packageAmount;
    private String jobLocation;
    private LocalDate applicationDeadline;
    private LocalDate driveDate;
    private String status;

    public PlacementDriveResponse(PlacementDrive drive) {
        this.id = drive.getId();
        this.companyId = drive.getCompany().getId();
        this.companyName = drive.getCompany().getCompanyName();
        this.jobRole = drive.getJobRole();
        this.jobDescription = drive.getJobDescription();
        this.packageAmount = drive.getPackageAmount();
        this.jobLocation = drive.getJobLocation();
        this.applicationDeadline = drive.getApplicationDeadline();
        this.driveDate = drive.getDriveDate();
        this.status = drive.getStatus();
    }

    public Long getId() {
        return id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobRole() {
        return jobRole;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public Double getPackageAmount() {
        return packageAmount;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public LocalDate getDriveDate() {
        return driveDate;
    }

    public String getStatus() {
        return status;
    }
}

