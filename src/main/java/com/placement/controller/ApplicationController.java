
package com.placement.controller;

import com.placement.dto.ApplicationResponse;
import com.placement.entity.Student;
import com.placement.entity.User;

import com.placement.repository.StudentRepository;
import com.placement.repository.UserRepository;

import com.placement.service.ApplicationService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public ApplicationController(
            ApplicationService applicationService,
            UserRepository userRepository,
            StudentRepository studentRepository) {

        this.applicationService = applicationService;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    // STUDENT: Apply for a placement drive
    @PostMapping("/apply/{driveId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApplicationResponse> applyForDrive(
            @PathVariable Long driveId,
            Principal principal) {

        Student student = getLoggedInStudent(principal);

        var application = applicationService.applyForDrive(
                student.getId(), driveId);

        return ResponseEntity.ok(
                new ApplicationResponse(application));
    }

    // STUDENT: View own applications
    @GetMapping("/my-applications")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(
            Principal principal) {

        Student student = getLoggedInStudent(principal);

        List<ApplicationResponse> responses =
                applicationService.getStudentApplications(student.getId())
                        .stream()
                        .map(ApplicationResponse::new)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    // ADMIN: View all applications
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {

        List<ApplicationResponse> responses =
                applicationService.getAllApplications().stream()
                        .map(ApplicationResponse::new)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    // ADMIN: View applications for a placement drive
    @GetMapping("/drive/{driveId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationResponse>> getDriveApplications(
            @PathVariable Long driveId) {

        List<ApplicationResponse> responses =
                applicationService.getDriveApplications(driveId).stream()
                        .map(ApplicationResponse::new)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    // ADMIN: Update application status
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApplicationResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        var application =
                applicationService.updateStatus(id, status);

        return ResponseEntity.ok(
                new ApplicationResponse(application));
    }

    // Find the student associated with the logged-in account
    private Student getLoggedInStudent(Principal principal) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return studentRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));
    }

    // Student withdraws their own application
    @PutMapping("/{applicationId}/withdraw")
@PreAuthorize("hasRole('STUDENT')")
public ResponseEntity<ApplicationResponse> withdrawApplication(
        @PathVariable Long applicationId,
        Principal principal) {

    Student student = getLoggedInStudent(principal);

    var application = applicationService.withdrawApplication(
            applicationId, student.getId());

    return ResponseEntity.ok(
            new ApplicationResponse(application));
}

}


