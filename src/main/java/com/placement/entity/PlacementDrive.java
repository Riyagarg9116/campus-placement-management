
package com.placement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "placement_drives")
public class PlacementDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String jobRole;

    private String jobDescription;

    private Double packageAmount;

    private String jobLocation;

    private LocalDate applicationDeadline;

    private LocalDate driveDate;

    private String status;

private Double minimumCgpa;

private String acceptedBranches;

private Integer maximumBacklogs;

private Integer graduationYear;

private String requiredSkills;


    public PlacementDrive() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

