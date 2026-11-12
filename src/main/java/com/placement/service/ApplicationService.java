
package com.placement.service;

import com.placement.entity.Application;
import com.placement.entity.Student;
import com.placement.entity.PlacementDrive;

import com.placement.repository.ApplicationRepository;
import com.placement.repository.StudentRepository;
import com.placement.repository.PlacementDriveRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final PlacementDriveRepository driveRepository;
    private final EligibilityService eligibilityService;

    private static final Set<String> VALID_STATUSES = Set.of(
            "APPLIED",
            "SHORTLISTED",
            "REJECTED",
            "SELECTED",
            "WITHDRAWN"
    );

    private static final Map<String, Set<String>> ALLOWED_TRANSITIONS = Map.of(
            "APPLIED", Set.of("SHORTLISTED", "REJECTED", "WITHDRAWN"),
            "SHORTLISTED", Set.of("SELECTED", "REJECTED", "WITHDRAWN"),
            "REJECTED", Set.of(),
            "SELECTED", Set.of(),
            "WITHDRAWN", Set.of()
    );

    public ApplicationService(
            ApplicationRepository applicationRepository,
            StudentRepository studentRepository,
            PlacementDriveRepository driveRepository,
            EligibilityService eligibilityService) {

        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
        this.driveRepository = driveRepository;
        this.eligibilityService = eligibilityService;
    }

    // Apply for a placement drive
    @Transactional
    public Application applyForDrive(Long studentId, Long driveId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        PlacementDrive drive = driveRepository.findById(driveId)
                .orElseThrow(() ->
                        new RuntimeException("Placement drive not found"));

        if (!"OPEN".equalsIgnoreCase(drive.getStatus())) {
            throw new RuntimeException("Placement drive is not open");
        }

        if (drive.getApplicationDeadline() == null ||
                drive.getApplicationDeadline().isBefore(LocalDate.now())) {
            throw new RuntimeException("Application deadline has passed");
        }

        if (applicationRepository.existsByStudentIdAndPlacementDriveId(
                studentId, driveId)) {
            throw new RuntimeException("Already applied for this drive");
        }

        if (!eligibilityService.checkEligibility(studentId, driveId)) {
            throw new RuntimeException("Student is not eligible for this drive");
        }

        Application application = new Application();
        application.setStudent(student);
        application.setPlacementDrive(drive);
        application.setStatus("APPLIED");

        return applicationRepository.save(application);
    }

    // Get all applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Get applications of a particular student
    public List<Application> getStudentApplications(Long studentId) {
        return applicationRepository.findByStudentId(studentId);
    }

    // Get applications for a particular placement drive
    public List<Application> getDriveApplications(Long driveId) {
        return applicationRepository.findByPlacementDriveId(driveId);
    }

    // Update application status
    @Transactional
    public Application updateStatus(Long id, String status) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));

        if (status == null || status.isBlank()) {
            throw new RuntimeException("Application status is required");
        }

        String normalizedStatus = status.trim().toUpperCase();

        if (!VALID_STATUSES.contains(normalizedStatus)) {
            throw new RuntimeException("Invalid application status");
        }

        String currentStatus = application.getStatus();

        if (normalizedStatus.equalsIgnoreCase(currentStatus)) {
            return application;
        }

        if (!ALLOWED_TRANSITIONS.getOrDefault(
                currentStatus.toUpperCase(), Set.of())
                .contains(normalizedStatus)) {
            throw new RuntimeException(
                    "Invalid status transition from "
                            + currentStatus + " to " + normalizedStatus);
        }

        application.setStatus(normalizedStatus);

        return applicationRepository.save(application);
    }

    // Student withdraws their own application
    @Transactional
    public Application withdrawApplication(Long applicationId, Long studentId) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));

        if (!application.getStudent().getId().equals(studentId)) {
            throw new RuntimeException(
                    "You are not authorized to withdraw this application");
        }

        if (!"APPLIED".equalsIgnoreCase(application.getStatus()) &&
                !"SHORTLISTED".equalsIgnoreCase(application.getStatus())) {
            throw new RuntimeException(
                    "This application cannot be withdrawn in its current status");
        }

        application.setStatus("WITHDRAWN");

        return applicationRepository.save(application);
    }
}



