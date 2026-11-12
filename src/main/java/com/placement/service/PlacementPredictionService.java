
package com.placement.service;

import com.placement.entity.PlacementPrediction;
import com.placement.entity.Student;

import com.placement.repository.PlacementPredictionRepository;
import com.placement.repository.StudentRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlacementPredictionService {

    private final PlacementPredictionRepository predictionRepository;
    private final StudentRepository studentRepository;

    public PlacementPredictionService(
            PlacementPredictionRepository predictionRepository,
            StudentRepository studentRepository) {
        this.predictionRepository = predictionRepository;
        this.studentRepository = studentRepository;
    }

    // Save ML prediction
    public PlacementPrediction savePrediction(
            Long studentId, PlacementPrediction prediction) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                    new RuntimeException("Student not found"));

        prediction.setStudent(student);

        return predictionRepository.save(prediction);
    }

    // Get all predictions of a student
    public List<PlacementPrediction> getStudentPredictions(Long studentId) {
        return predictionRepository.findByStudentId(studentId);
    }

    // Get latest prediction
    public PlacementPrediction getLatestPrediction(Long studentId) {
        return predictionRepository
                .findTopByStudentIdOrderByPredictionDateDesc(studentId)
                .orElseThrow(() ->
                    new RuntimeException("Prediction not found"));
    }

    // Get all predictions
    public List<PlacementPrediction> getAllPredictions() {
        return predictionRepository.findAll();
    }
}

