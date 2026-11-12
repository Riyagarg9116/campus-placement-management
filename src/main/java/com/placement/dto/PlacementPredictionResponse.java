
package com.placement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlacementPredictionResponse {

    private String prediction;

    @JsonProperty("placement_probability")
    private Double placementProbability;

    public PlacementPredictionResponse() {
    }

    public PlacementPredictionResponse(
            String prediction,
            Double placementProbability) {
        this.prediction = prediction;
        this.placementProbability = placementProbability;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public Double getPlacementProbability() {
        return placementProbability;
    }

    public void setPlacementProbability(Double placementProbability) {
        this.placementProbability = placementProbability;
    }
}


