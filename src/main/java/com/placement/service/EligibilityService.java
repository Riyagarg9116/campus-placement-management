
package com.placement.service;

import com.placement.entity.Student;
import com.placement.entity.EligibilityCriteria;

import com.placement.repository.StudentRepository;
import com.placement.repository.EligibilityCriteriaRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class EligibilityService {

    private final StudentRepository studentRepository;
    private final EligibilityCriteriaRepository criteriaRepository;

    public EligibilityService(
            StudentRepository studentRepository,
            EligibilityCriteriaRepository criteriaRepository) {

        this.studentRepository = studentRepository;
        this.criteriaRepository = criteriaRepository;
    }

    public boolean checkEligibility(Long studentId, Long driveId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        EligibilityCriteria criteria =
                criteriaRepository.findByPlacementDriveId(driveId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Eligibility criteria not found"));

        // Validate student CGPA
        BigDecimal cgpa = student.getCgpa();

        if (cgpa == null ||
                cgpa.compareTo(BigDecimal.ZERO) < 0 ||
                cgpa.compareTo(BigDecimal.TEN) > 0) {
            return false;
        }

        // Validate minimum CGPA
        BigDecimal minimumCgpa = criteria.getMinimumCgpa();

        if (minimumCgpa == null ||
                minimumCgpa.compareTo(BigDecimal.ZERO) < 0 ||
                minimumCgpa.compareTo(BigDecimal.TEN) > 0) {
            throw new RuntimeException("Invalid eligibility CGPA criteria");
        }

        if (cgpa.compareTo(minimumCgpa) < 0) {
            return false;
        }

        // Check maximum backlogs
        Integer studentBacklogs = student.getActiveBacklogs();
        Integer maximumBacklogs = criteria.getMaximumBacklogs();

        if (studentBacklogs == null || studentBacklogs < 0) {
            return false;
        }

        if (maximumBacklogs != null) {

            if (maximumBacklogs < 0) {
                throw new RuntimeException(
                        "Maximum backlogs cannot be negative");
            }

            if (studentBacklogs > maximumBacklogs) {
                return false;
            }
        }

        // Check eligible branches
        String eligibleBranches = criteria.getEligibleBranches();
        String studentBranch = student.getBranch();

        if (eligibleBranches != null &&
                !eligibleBranches.isBlank()) {

            if (studentBranch == null || studentBranch.isBlank()) {
                return false;
            }

            List<String> branches = Arrays.stream(
                            eligibleBranches.split(","))
                    .map(String::trim)
                    .filter(branch -> !branch.isEmpty())
                    .map(String::toLowerCase)
                    .toList();

            if (!branches.contains(studentBranch.trim().toLowerCase())) {
                return false;
            }
        }

        // Check graduation year
        Integer graduationYear = student.getGraduationYear();

        if (graduationYear == null || graduationYear < 2020) {
            return false;
        }

        Integer minimumYear = criteria.getMinimumGraduationYear();
        Integer maximumYear = criteria.getMaximumGraduationYear();

        if (minimumYear != null && maximumYear != null &&
                minimumYear > maximumYear) {
            throw new RuntimeException(
                    "Minimum graduation year cannot exceed maximum graduation year");
        }

        if (minimumYear != null && graduationYear < minimumYear) {
            return false;
        }

        if (maximumYear != null && graduationYear > maximumYear) {
            return false;
        }

        return true;
    }
}



