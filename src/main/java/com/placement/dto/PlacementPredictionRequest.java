
package com.placement.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlacementPredictionRequest {

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double cgpa;

    @NotNull
    @Min(0)
    @Max(50)
    private Integer internships;

    @NotNull
    @Min(0)
    @Max(50)
    private Integer projects;

    @NotNull
    @Min(0)
    @Max(50)
    private Integer workshopsCertifications;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double aptitudeTestScore;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double softSkillsRating;

    @NotBlank
    private String extracurricularActivities;

    @NotBlank
    private String placementTraining;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double sscMarks;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double hscMarks;

    public PlacementPredictionRequest() {}

    public Double getCgpa() { return cgpa; }
    public void setCgpa(Double cgpa) { this.cgpa = cgpa; }

    public Integer getInternships() { return internships; }
    public void setInternships(Integer internships) { this.internships = internships; }

    public Integer getProjects() { return projects; }
    public void setProjects(Integer projects) { this.projects = projects; }

    public Integer getWorkshopsCertifications() { return workshopsCertifications; }
    public void setWorkshopsCertifications(Integer workshopsCertifications) {
        this.workshopsCertifications = workshopsCertifications;
    }

    public Double getAptitudeTestScore() { return aptitudeTestScore; }
    public void setAptitudeTestScore(Double aptitudeTestScore) {
        this.aptitudeTestScore = aptitudeTestScore;
    }

    public Double getSoftSkillsRating() { return softSkillsRating; }
    public void setSoftSkillsRating(Double softSkillsRating) {
        this.softSkillsRating = softSkillsRating;
    }

    public String getExtracurricularActivities() { return extracurricularActivities; }
    public void setExtracurricularActivities(String extracurricularActivities) {
        this.extracurricularActivities = extracurricularActivities;
    }

    public String getPlacementTraining() { return placementTraining; }
    public void setPlacementTraining(String placementTraining) {
        this.placementTraining = placementTraining;
    }

    public Double getSscMarks() { return sscMarks; }
    public void setSscMarks(Double sscMarks) { this.sscMarks = sscMarks; }

    public Double getHscMarks() { return hscMarks; }
    public void setHscMarks(Double hscMarks) { this.hscMarks = hscMarks; }
}

