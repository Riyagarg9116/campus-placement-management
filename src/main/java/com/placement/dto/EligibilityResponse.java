
package com.placement.dto;

import com.placement.entity.EligibilityCriteria;

import java.math.BigDecimal;

public class EligibilityResponse {

    private Long id;
    private Long driveId;
    private String jobRole;
    private String companyName;
    private BigDecimal minimumCgpa;
    private Integer maximumBacklogs;
    private String eligibleBranches;
    private Integer minimumGraduationYear;
    private Integer maximumGraduationYear;

    public EligibilityResponse(EligibilityCriteria criteria) {

        this.id = criteria.getId();
        this.driveId = criteria.getPlacementDrive().getId();
        this.jobRole = criteria.getPlacementDrive().getJobRole();
        this.companyName = criteria.getPlacementDrive()
                .getCompany().getCompanyName();

        this.minimumCgpa = criteria.getMinimumCgpa();
        this.maximumBacklogs = criteria.getMaximumBacklogs();
        this.eligibleBranches = criteria.getEligibleBranches();
        this.minimumGraduationYear =
                criteria.getMinimumGraduationYear();
        this.maximumGraduationYear =
                criteria.getMaximumGraduationYear();
    }

    public Long getId() {
        return id;
    }

    public Long getDriveId() {
        return driveId;
    }

    public String getJobRole() {
        return jobRole;
    }

    public String getCompanyName() {
        return companyName;
    }

    public BigDecimal getMinimumCgpa() {
        return minimumCgpa;
    }

    public Integer getMaximumBacklogs() {
        return maximumBacklogs;
    }

    public String getEligibleBranches() {
        return eligibleBranches;
    }

    public Integer getMinimumGraduationYear() {
        return minimumGraduationYear;
    }

    public Integer getMaximumGraduationYear() {
        return maximumGraduationYear;
    }
}

