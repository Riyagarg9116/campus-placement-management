
package com.placement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class PlacementDriveRequest {

    @NotNull(message = "Company ID is required")
    private Long companyId;

    @NotBlank(message = "Job role is required")
    private String jobRole;

    private String jobDescription;

    @NotNull(message = "Package amount is required")
    @Positive(message = "Package amount must be positive")
    private Double packageAmount;

    private String jobLocation;

    @NotNull(message = "Application deadline is required")
    private LocalDate applicationDeadline;

    @NotNull(message = "Drive date is required")
    private LocalDate driveDate;

    private String status;

    private Double minimumCgpa;

    private String acceptedBranches;

    private Integer maximumBacklogs;

    private Integer graduationYear;

    private String requiredSkills;

    public PlacementDriveRequest() {
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Double getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(Double packageAmount) {
        this.packageAmount = packageAmount;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public LocalDate getDriveDate() {
        return driveDate;
    }

    public void setDriveDate(LocalDate driveDate) {
        this.driveDate = driveDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getMinimumCgpa() {
        return minimumCgpa;
    }

    public void setMinimumCgpa(Double minimumCgpa) {
        this.minimumCgpa = minimumCgpa;
    }

    public String getAcceptedBranches() {
        return acceptedBranches;
    }

    public void setAcceptedBranches(String acceptedBranches) {
        this.acceptedBranches = acceptedBranches;
    }

    public Integer getMaximumBacklogs() {
        return maximumBacklogs;
    }

    public void setMaximumBacklogs(Integer maximumBacklogs) {
        this.maximumBacklogs = maximumBacklogs;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Integer graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }
}
