
package com.placement.controller;

import com.placement.dto.AdminDashboardResponse;
import com.placement.dto.StudentDashboardResponse;
import com.placement.entity.Student;
import com.placement.entity.User;
import com.placement.repository.StudentRepository;
import com.placement.repository.UserRepository;
import com.placement.service.DashboardService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public DashboardController(
            DashboardService dashboardService,
            UserRepository userRepository,
            StudentRepository studentRepository) {

        this.dashboardService = dashboardService;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    // STUDENT DASHBOARD
    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentDashboardResponse> getStudentDashboard(
            Principal principal) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        StudentDashboardResponse dashboard =
                dashboardService.getStudentDashboard(student.getId());

        return ResponseEntity.ok(dashboard);
    }

    // ADMIN DASHBOARD
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminDashboardResponse> getAdminDashboard() {

        AdminDashboardResponse dashboard =
                dashboardService.getAdminDashboard();

        return ResponseEntity.ok(dashboard);
    }
}


