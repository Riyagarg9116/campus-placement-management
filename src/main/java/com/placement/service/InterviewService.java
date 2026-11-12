
package com.placement.service;

import com.placement.entity.Interview;
import com.placement.entity.Application;

import com.placement.repository.InterviewRepository;
import com.placement.repository.ApplicationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;

    private static final Set<String> VALID_STATUSES = Set.of(
            "SCHEDULED",
            "COMPLETED",
            "CANCELLED",
            "PASSED",
            "FAILED"
    );

    private static final Map<String, Set<String>> ALLOWED_TRANSITIONS = Map.of(
            "SCHEDULED", Set.of("COMPLETED", "CANCELLED"),
            "COMPLETED", Set.of("PASSED", "FAILED"),
            "CANCELLED", Set.of(),
            "PASSED", Set.of(),
            "FAILED", Set.of()
    );

    public InterviewService(
            InterviewRepository interviewRepository,
            ApplicationRepository applicationRepository) {

        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
    }

    // Schedule interview
    @Transactional
    public Interview scheduleInterview(
            Long applicationId, Interview interview) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));

        if ("REJECTED".equalsIgnoreCase(application.getStatus()) ||
                "WITHDRAWN".equalsIgnoreCase(application.getStatus())) {
            throw new RuntimeException(
                    "Cannot schedule interview for this application");
        }

        if (interview.getInterviewDate() == null ||
                !interview.getInterviewDate().isAfter(LocalDateTime.now())) {
            throw new RuntimeException(
                    "Interview date must be in the future");
        }

        interview.setApplication(application);
        interview.setStatus("SCHEDULED");

        return interviewRepository.save(interview);
    }

    // Get all interviews
    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    // Get interviews for an application
    public List<Interview> getApplicationInterviews(Long applicationId) {
        return interviewRepository.findByApplicationId(applicationId);
    }

    // Update interview status
    @Transactional
    public Interview updateInterviewStatus(Long id, String status) {

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Interview not found"));

        if (status == null || status.isBlank()) {
            throw new RuntimeException("Interview status is required");
        }

        String normalizedStatus = status.trim().toUpperCase();

        if (!VALID_STATUSES.contains(normalizedStatus)) {
            throw new RuntimeException("Invalid interview status");
        }

        String currentStatus = interview.getStatus().toUpperCase();

        if (currentStatus.equals(normalizedStatus)) {
            return interview;
        }

        if (!ALLOWED_TRANSITIONS.getOrDefault(
                currentStatus, Set.of()).contains(normalizedStatus)) {
            throw new RuntimeException(
                    "Invalid interview status transition from "
                            + currentStatus + " to " + normalizedStatus);
        }

        interview.setStatus(normalizedStatus);

        return interviewRepository.save(interview);
    }

    // Update interview feedback
    @Transactional
    public Interview updateFeedback(Long id, String feedback) {

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Interview not found"));

        if (feedback == null || feedback.isBlank()) {
            throw new RuntimeException("Feedback cannot be empty");
        }

        if ("CANCELLED".equalsIgnoreCase(interview.getStatus())) {
            throw new RuntimeException(
                    "Cannot add feedback to a cancelled interview");
        }

        interview.setFeedback(feedback.trim());

        return interviewRepository.save(interview);
    }

    // Get all interviews belonging to a student
    public List<Interview> getInterviewsForStudent(Long studentId) {
        return interviewRepository.findByApplicationStudentId(studentId);
    }
}



