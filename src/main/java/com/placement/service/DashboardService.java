

package com.placement.service;

import com.placement.dto.AdminDashboardResponse;
import com.placement.dto.StudentDashboardResponse;

import com.placement.entity.Application;
import com.placement.entity.Interview;

import com.placement.repository.ApplicationRepository;
import com.placement.repository.InterviewRepository;
import com.placement.repository.PlacementDriveRepository;
import com.placement.repository.UserRepository;
import com.placement.repository.CompanyRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;
    private final PlacementDriveRepository placementDriveRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public DashboardService(
            ApplicationRepository applicationRepository,
            InterviewRepository interviewRepository,
            PlacementDriveRepository placementDriveRepository,
            UserRepository userRepository,
            CompanyRepository companyRepository) {

        this.applicationRepository = applicationRepository;
        this.interviewRepository = interviewRepository;
        this.placementDriveRepository = placementDriveRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    // STUDENT DASHBOARD
    public StudentDashboardResponse getStudentDashboard(Long studentId) {

        List<Application> applications =
                applicationRepository.findByStudentId(studentId);

        List<Interview> interviews =
                interviewRepository.findByApplicationStudentId(studentId);

        long totalAvailableDrives =
                placementDriveRepository.findByStatusIgnoreCase("OPEN").size();

        long totalApplications = applications.size();

        long shortlistedApplications = applications.stream()
                .filter(application ->
                        "SHORTLISTED".equalsIgnoreCase(application.getStatus()))
                .count();

        long selectedApplications = applications.stream()
                .filter(application ->
                        "SELECTED".equalsIgnoreCase(application.getStatus()))
                .count();

        long upcomingInterviews = interviews.stream()
                .filter(interview ->
                        interview.getInterviewDate() != null &&
                        interview.getInterviewDate().isAfter(LocalDateTime.now()) &&
                        "SCHEDULED".equalsIgnoreCase(interview.getStatus()))
                .count();

        return new StudentDashboardResponse(
                totalAvailableDrives,
                totalApplications,
                shortlistedApplications,
                upcomingInterviews,
                selectedApplications
        );
    }

    // ADMIN DASHBOARD
    public AdminDashboardResponse getAdminDashboard() {

        long totalStudents = userRepository.findAll().stream()
                .filter(user ->
                        "STUDENT".equalsIgnoreCase(user.getRole()))
                .count();

        long totalCompanies = companyRepository.count();

        long totalDrives = placementDriveRepository.count();

        long totalApplications = applicationRepository.count();

        long totalSelectedStudents = applicationRepository
                .findByStatusIgnoreCase("SELECTED").size();

        return new AdminDashboardResponse(
                totalStudents,
                totalCompanies,
                totalDrives,
                totalApplications,
                totalSelectedStudents
        );
    }
}
