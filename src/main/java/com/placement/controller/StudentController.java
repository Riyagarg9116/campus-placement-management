
package com.placement.controller;
import com.placement.dto.AdminStudentRequest;
import com.placement.dto.AdminStudentRequest;
import com.placement.dto.StudentRequest;
import com.placement.dto.StudentResponse;
import com.placement.entity.Student;
import com.placement.entity.User;
import com.placement.repository.UserRepository;
import com.placement.repository.StudentRepository;
import com.placement.service.StudentService;
import com.placement.service.ResumeStorageService;

import jakarta.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")

public class StudentController {

    private final StudentService studentService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ResumeStorageService resumeStorageService;

    public StudentController(
            StudentService studentService,
            UserRepository userRepository,
            StudentRepository studentRepository,
            ResumeStorageService resumeStorageService) {

        this.studentService = studentService;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.resumeStorageService = resumeStorageService;
    }

    // Get logged-in student's own profile
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/profile")
    public ResponseEntity<StudentResponse> getMyProfile(
            Authentication authentication) {

        User user = getLoggedInUser(authentication);

        StudentResponse response =
                studentService.getStudentByUserId(user.getId());

        return ResponseEntity.ok(response);
    }

    // Create logged-in student's profile
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/profile")
    public ResponseEntity<StudentResponse> createStudent(
            Authentication authentication,
            @Valid @RequestBody StudentRequest request) {

        User user = getLoggedInUser(authentication);

        StudentResponse response =
                studentService.createStudent(user.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // Get student by ID - own profile only
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(
            @PathVariable Long id,
            Authentication authentication) {

        User user = getLoggedInUser(authentication);

        StudentResponse response =
                studentService.getStudentById(id);

        if (!response.getUserId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(response);
    }

    // Get student by registration number - own profile only
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/registration/{registrationNumber}")
    public ResponseEntity<StudentResponse> getStudentByRegistrationNumber(
            @PathVariable String registrationNumber,
            Authentication authentication) {

        User user = getLoggedInUser(authentication);

        StudentResponse response =
                studentService.getStudentByRegistrationNumber(
                        registrationNumber);

        if (!response.getUserId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(response);
    }

    // Update logged-in student's own profile
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/profile")
    public ResponseEntity<StudentResponse> updateStudent(
            Authentication authentication,
            @Valid @RequestBody StudentRequest request) {

        User user = getLoggedInUser(authentication);

        String registrationNumber =
                studentService.getRegistrationNumberByUserId(user.getId());

        StudentResponse existingStudent =
                studentService.getStudentByRegistrationNumber(
                        registrationNumber);

        StudentResponse response =
                studentService.updateStudent(
                        existingStudent.getId(), request);

        return ResponseEntity.ok(response);
    }

    // Upload or replace resume
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping(
            value = "/profile/resume",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadResume(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {

        User user = getLoggedInUser(authentication);

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        String oldResume = student.getResumeUrl();

        String resumePath =
                resumeStorageService.storeResume(file, student.getId());

        student.setResumeUrl(resumePath);
        studentRepository.save(student);

        if (oldResume != null && !oldResume.isBlank()) {
            resumeStorageService.deleteResume(oldResume);
        }

        return ResponseEntity.ok(
                Map.of("message", "Resume uploaded successfully"));
    }

    // Download logged-in student's resume
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/profile/resume")
    public ResponseEntity<Resource> downloadResume(
            Authentication authentication) {

        User user = getLoggedInUser(authentication);

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        String resumePath = student.getResumeUrl();

        if (resumePath == null || resumePath.isBlank()) {
            return ResponseEntity.notFound().build();
        }

        Path path = resumeStorageService.getResumePath(resumePath);

        try {
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" +
                                    path.getFileName().toString() + "\"")
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException("Could not download resume", e);
        }
    }

    // Remove logged-in student's resume
    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/profile/resume")
    public ResponseEntity<?> deleteResume(
            Authentication authentication) {

        User user = getLoggedInUser(authentication);

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        String resumePath = student.getResumeUrl();

        if (resumePath == null || resumePath.isBlank()) {
            return ResponseEntity.notFound().build();
        }

        resumeStorageService.deleteResume(resumePath);

        student.setResumeUrl(null);
        studentRepository.save(student);

        return ResponseEntity.ok(
                Map.of("message", "Resume removed successfully"));
    }
    // Admin: Get all registered students
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin/all")
public ResponseEntity<java.util.List<StudentResponse>> getAllStudents() {

    return ResponseEntity.ok(studentService.getAllStudents());
}
// Admin: Create a new student account
@PreAuthorize("hasRole('ADMIN')")
@PostMapping("/admin/create")
public ResponseEntity<StudentResponse> createStudentByAdmin(
        @Valid @RequestBody AdminStudentRequest request) {

    StudentResponse response =
            studentService.createStudentByAdmin(request);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
}

    // Helper method to identify logged-in user
    private User getLoggedInUser(Authentication authentication) {

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}
