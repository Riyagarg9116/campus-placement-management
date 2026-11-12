
package com.placement.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class EligibilityRequest {

    @NotNull(message = "Drive ID is required")
    @Min(value = 1, message = "Drive ID must be positive")
    private Long driveId;

    @NotNull(message = "Minimum CGPA is required")
    @DecimalMin(value = "0.0", message = "CGPA cannot be below 0")
    @DecimalMax(value = "10.0", message = "CGPA cannot exceed 10")
    private BigDecimal minimumCgpa;

    @NotNull(message = "Maximum backlogs is required")
    @Min(value = 0, message = "Backlogs cannot be negative")
    private Integer maximumBacklogs;

    @NotBlank(message = "Eligible branches are required")
    @Size(max = 500, message = "Eligible branches are too long")
    private String eligibleBranches;

    @NotNull(message = "Minimum graduation year is required")
    @Min(value = 2020, message = "Invalid minimum graduation year")
    private Integer minimumGraduationYear;

    @NotNull(message = "Maximum graduation year is required")
    @Min(value = 2020, message = "Invalid maximum graduation year")
    private Integer maximumGraduationYear;

    public EligibilityRequest() {
    }

    public Long getDriveId() {
        return driveId;
    }

    public void setDriveId(Long driveId) {
        this.driveId = driveId;
    }

    public BigDecimal getMinimumCgpa() {
        return minimumCgpa;
    }

    public void setMinimumCgpa(BigDecimal minimumCgpa) {
        this.minimumCgpa = minimumCgpa;
    }

    public Integer getMaximumBacklogs() {
        return maximumBacklogs;
    }

    public void setMaximumBacklogs(Integer maximumBacklogs) {
        this.maximumBacklogs = maximumBacklogs;
    }

    public String getEligibleBranches() {
        return eligibleBranches;
    }

    public void setEligibleBranches(String eligibleBranches) {
        this.eligibleBranches = eligibleBranches;
    }

    public Integer getMinimumGraduationYear() {
        return minimumGraduationYear;
    }

    public void setMinimumGraduationYear(Integer minimumGraduationYear) {
        this.minimumGraduationYear = minimumGraduationYear;
    }

    public Integer getMaximumGraduationYear() {
        return maximumGraduationYear;
    }

    public void setMaximumGraduationYear(Integer maximumGraduationYear) {
        this.maximumGraduationYear = maximumGraduationYear;
    }
}


