

package com.placement.controller;

import com.placement.dto.PlacementPredictionRequest;
import com.placement.dto.PlacementPredictionResponse;
import com.placement.entity.User;
import com.placement.entity.Student;
import com.placement.repository.UserRepository;
import com.placement.repository.StudentRepository;
import com.placement.service.MlPredictionService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ml")
public class MlPredictionController {

    private final MlPredictionService mlPredictionService;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public MlPredictionController(
            MlPredictionService mlPredictionService,
            StudentRepository studentRepository,
            UserRepository userRepository) {

        this.mlPredictionService = mlPredictionService;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/predict")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<PlacementPredictionResponse> predictPlacement(
            @Valid @RequestBody PlacementPredictionRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        PlacementPredictionResponse result =
                mlPredictionService.predict(request, student.getId());

        return ResponseEntity.ok(result);
    }
}
