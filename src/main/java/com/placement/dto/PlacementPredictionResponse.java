
package com.placement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlacementPredictionResponse {

    @JsonProperty("placement_probability")
    private Double placementProbability;

    private String prediction;

    public Double getPlacementProbability() {
        return placementProbability;
    }

    public void setPlacementProbability(Double placementProbability) {
        this.placementProbability = placementProbability;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }
}
