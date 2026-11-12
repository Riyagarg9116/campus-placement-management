

package com.placement.controller;

import com.placement.dto.InterviewRequest;
import com.placement.dto.InterviewResponse;

import com.placement.entity.Student;
import com.placement.entity.User;

import com.placement.repository.UserRepository;
import com.placement.repository.StudentRepository;

import com.placement.service.InterviewService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@CrossOrigin(origins = "*")
public class InterviewController {

    private final InterviewService interviewService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public InterviewController(
            InterviewService interviewService,
            UserRepository userRepository,
            StudentRepository studentRepository) {

        this.interviewService = interviewService;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    // ADMIN: Schedule an interview
    @PostMapping("/schedule/{applicationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InterviewResponse> scheduleInterview(
            @PathVariable Long applicationId,
            @Valid @RequestBody InterviewRequest request) {

        var interview = new com.placement.entity.Interview();

        interview.setInterviewRound(request.getInterviewRound());
        interview.setInterviewDate(request.getInterviewDate());
        interview.setInterviewMode(request.getInterviewMode());
        interview.setInterviewLocation(request.getInterviewLocation());

        var savedInterview = interviewService.scheduleInterview(
                applicationId, interview);

        return ResponseEntity.ok(
                new InterviewResponse(savedInterview));
    }

    // ADMIN: View all interviews
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InterviewResponse>> getAllInterviews() {

        List<InterviewResponse> responses =
                interviewService.getAllInterviews().stream()
                        .map(InterviewResponse::new)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    // ADMIN: View interviews for an application
    @GetMapping("/application/{applicationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InterviewResponse>> getApplicationInterviews(
            @PathVariable Long applicationId) {

        List<InterviewResponse> responses =
                interviewService.getApplicationInterviews(applicationId)
                        .stream()
                        .map(InterviewResponse::new)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    // STUDENT: View their own interviews
    @GetMapping("/my-interviews")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<InterviewResponse>> getMyInterviews(
            Principal principal) {

        Student student = getLoggedInStudent(principal);

        List<InterviewResponse> responses =
                interviewService.getInterviewsForStudent(student.getId())
                        .stream()
                        .map(InterviewResponse::new)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    // ADMIN: Update interview status
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InterviewResponse> updateInterviewStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        var interview =
                interviewService.updateInterviewStatus(id, status);

        return ResponseEntity.ok(
                new InterviewResponse(interview));
    }

    // ADMIN: Update interview feedback
    @PutMapping("/{id}/feedback")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InterviewResponse> updateFeedback(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, String> request) {

        var interview = interviewService.updateFeedback(
                id, request.get("feedback"));

        return ResponseEntity.ok(
                new InterviewResponse(interview));
    }

    // Identify logged-in student
    private Student getLoggedInStudent(Principal principal) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return studentRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));
    }
}
