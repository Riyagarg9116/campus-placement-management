
package com.placement.service;

import com.placement.dto.PlacementPredictionRequest;
import com.placement.dto.PlacementPredictionResponse;
import com.placement.entity.PlacementPrediction;
import com.placement.entity.Student;
import com.placement.repository.PlacementPredictionRepository;
import com.placement.repository.StudentRepository;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class MlPredictionService {

    private final RestClient restClient;
    private final PlacementPredictionRepository predictionRepository;
    private final StudentRepository studentRepository;

    public MlPredictionService(
            RestClient.Builder builder,
            PlacementPredictionRepository predictionRepository,
            StudentRepository studentRepository) {

        this.restClient = builder
                .baseUrl("http://localhost:5000")
                .build();

        this.predictionRepository = predictionRepository;
        this.studentRepository = studentRepository;
    }

    public PlacementPredictionResponse predict(
            PlacementPredictionRequest request,
            Long studentId) {

        Map<String, Object> payload = Map.of(
                "CGPA", request.getCgpa(),
                "Internships", request.getInternships(),
                "Projects", request.getProjects(),
                "Workshops/Certifications", request.getWorkshopsCertifications(),
                "AptitudeTestScore", request.getAptitudeTestScore(),
                "SoftSkillsRating", request.getSoftSkillsRating(),
                "ExtracurricularActivities", request.getExtracurricularActivities(),
                "PlacementTraining", request.getPlacementTraining(),
                "SSC_Marks", request.getSscMarks(),
                "HSC_Marks", request.getHscMarks()
        );

        PlacementPredictionResponse response = restClient.post()
                .uri("/predict")
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .body(PlacementPredictionResponse.class);

        if (response == null) {
            throw new IllegalStateException("ML service returned an empty response");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        PlacementPrediction prediction = new PlacementPrediction();
        prediction.setStudent(student);
        prediction.setPlacementProbability(response.getPlacementProbability());
        prediction.setPredictionCategory(response.getPrediction());
        prediction.setModelVersion("Placement Model v1");

        predictionRepository.save(prediction);

        return response;
    }
}


