
package com.placement.controller;

import com.placement.dto.EligibilityRequest;
import com.placement.dto.EligibilityResponse;

import com.placement.entity.EligibilityCriteria;
import com.placement.entity.PlacementDrive;
import com.placement.entity.Student;
import com.placement.entity.User;

import com.placement.repository.EligibilityCriteriaRepository;
import com.placement.repository.PlacementDriveRepository;
import com.placement.repository.StudentRepository;
import com.placement.repository.UserRepository;

import com.placement.service.EligibilityService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/eligibility")
@CrossOrigin(origins = "*")
@Validated
public class EligibilityController {

    private final EligibilityCriteriaRepository criteriaRepository;
    private final PlacementDriveRepository driveRepository;
    private final EligibilityService eligibilityService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public EligibilityController(
            EligibilityCriteriaRepository criteriaRepository,
            PlacementDriveRepository driveRepository,
            EligibilityService eligibilityService,
            UserRepository userRepository,
            StudentRepository studentRepository) {

        this.criteriaRepository = criteriaRepository;
        this.driveRepository = driveRepository;
        this.eligibilityService = eligibilityService;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    // ADMIN: Create or update eligibility criteria
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EligibilityResponse> saveCriteria(
            @Valid @RequestBody EligibilityRequest request) {

        PlacementDrive drive = driveRepository.findById(
                request.getDriveId())
                .orElseThrow(() ->
                        new RuntimeException("Placement drive not found"));

        if (request.getMinimumGraduationYear() >
                request.getMaximumGraduationYear()) {
            throw new IllegalArgumentException(
                    "Minimum graduation year cannot exceed maximum year");
        }

        String branches = request.getEligibleBranches();

        boolean hasValidBranch = branches != null &&
                !branches.isBlank() &&
                Arrays.stream(branches.split(","))
                        .map(String::trim)
                        .anyMatch(branch -> !branch.isEmpty());

        if (!hasValidBranch) {
            throw new IllegalArgumentException(
                    "At least one valid eligible branch is required");
        }

        EligibilityCriteria criteria =
                criteriaRepository.findByPlacementDriveId(
                        request.getDriveId())
                        .orElse(new EligibilityCriteria());

        criteria.setPlacementDrive(drive);
        criteria.setMinimumCgpa(request.getMinimumCgpa());
        criteria.setMaximumBacklogs(request.getMaximumBacklogs());
        criteria.setEligibleBranches(branches.trim());
        criteria.setMinimumGraduationYear(
                request.getMinimumGraduationYear());
        criteria.setMaximumGraduationYear(
                request.getMaximumGraduationYear());

        EligibilityCriteria saved =
                criteriaRepository.save(criteria);

        return ResponseEntity.ok(new EligibilityResponse(saved));
    }

    // ADMIN and STUDENT: Get criteria for a particular drive
    @GetMapping("/drive/{driveId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<EligibilityResponse> getCriteria(
            @PathVariable @Positive Long driveId) {

        EligibilityCriteria criteria =
                criteriaRepository.findByPlacementDriveId(driveId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Eligibility criteria not found"));

        return ResponseEntity.ok(new EligibilityResponse(criteria));
    }

    // ADMIN: Get all eligibility criteria
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EligibilityResponse>> getAllCriteria() {

        List<EligibilityResponse> responses =
                criteriaRepository.findAll().stream()
                        .map(EligibilityResponse::new)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    // STUDENT: Check eligibility using their logged-in account
    @GetMapping("/check/{driveId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Boolean> checkMyEligibility(
            @PathVariable @Positive Long driveId,
            java.security.Principal principal) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        boolean eligible = eligibilityService.checkEligibility(
                student.getId(), driveId);

        return ResponseEntity.ok(eligible);
    }
}

