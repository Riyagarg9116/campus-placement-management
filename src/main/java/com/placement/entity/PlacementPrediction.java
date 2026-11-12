
package com.placement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "placement_predictions")
public class PlacementPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private Double placementProbability;

    private String predictionCategory;

    private String modelVersion;

    private LocalDateTime predictionDate;

    public PlacementPrediction() {
    }

    @PrePersist
    protected void onCreate() {
        predictionDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getPlacementProbability() {
        return placementProbability;
    }

    public void setPlacementProbability(Double placementProbability) {
        this.placementProbability = placementProbability;
    }

    public String getPredictionCategory() {
        return predictionCategory;
    }

    public void setPredictionCategory(String predictionCategory) {
        this.predictionCategory = predictionCategory;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public LocalDateTime getPredictionDate() {
        return predictionDate;
    }
}

