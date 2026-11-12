
package com.placement.controller;

import com.placement.dto.StudentRequest;
import com.placement.dto.StudentResponse;
import com.placement.service.StudentService;
import com.placement.repository.UserRepository;
import com.placement.entity.User;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    private final StudentService studentService;
    private final UserRepository userRepository;

    public StudentController(
            StudentService studentService,
            UserRepository userRepository) {

        this.studentService = studentService;
        this.userRepository = userRepository;
    }

    // Create logged-in student's profile
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

    // Helper method to identify logged-in user
    private User getLoggedInUser(Authentication authentication) {

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}



