
package com.placement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "eligibility_criteria")
public class EligibilityCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "drive_id", nullable = false, unique = true)
    private PlacementDrive placementDrive;

    @Column(precision = 4, scale = 2)
    private BigDecimal minimumCgpa;

    private Integer maximumBacklogs;

    private String eligibleBranches;

    private Integer minimumGraduationYear;

    private Integer maximumGraduationYear;

    public EligibilityCriteria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlacementDrive getPlacementDrive() {
        return placementDrive;
    }

    public void setPlacementDrive(PlacementDrive placementDrive) {
        this.placementDrive = placementDrive;
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

