
package com.placement.service;

import com.placement.entity.Student;
import com.placement.entity.PlacementDrive;

import com.placement.repository.StudentRepository;
import com.placement.repository.PlacementDriveRepository;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EligibilityService {

    private final StudentRepository studentRepository;
    private final PlacementDriveRepository driveRepository;

    public EligibilityService(
            StudentRepository studentRepository,
            PlacementDriveRepository driveRepository) {

        this.studentRepository = studentRepository;
        this.driveRepository = driveRepository;
    }

    public boolean checkEligibility(Long studentId, Long driveId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        PlacementDrive drive = driveRepository.findById(driveId)
                .orElseThrow(() ->
                        new RuntimeException("Placement drive not found"));

        // Check CGPA
        Double cgpa = student.getCgpa() == null
                ? null : student.getCgpa().doubleValue();

        Double minimumCgpa = drive.getMinimumCgpa();

        if (cgpa == null || minimumCgpa == null || cgpa < minimumCgpa) {
            return false;
        }

        if (cgpa < 0 || cgpa > 10 ||
                minimumCgpa < 0 || minimumCgpa > 10) {
            return false;
        }

        // Check active backlogs
        Integer backlogs = student.getActiveBacklogs();
        Integer maximumBacklogs = drive.getMaximumBacklogs();

        if (backlogs == null || backlogs < 0 ||
                maximumBacklogs == null || maximumBacklogs < 0) {
            return false;
        }

        if (backlogs > maximumBacklogs) {
            return false;
        }

        // Check branch
        String acceptedBranches = drive.getAcceptedBranches();
        String studentBranch = student.getBranch();

        if (acceptedBranches == null || acceptedBranches.isBlank() ||
                studentBranch == null || studentBranch.isBlank()) {
            return false;
        }

        List<String> branches = Arrays.stream(acceptedBranches.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();

        if (!branches.contains(studentBranch.trim().toLowerCase())) {
            return false;
        }

        // Check graduation year
        Integer graduationYear = student.getGraduationYear();
        Integer requiredYear = drive.getGraduationYear();

        if (graduationYear == null || requiredYear == null ||
                graduationYear < requiredYear) {
            return false;
        }

        // Check required skills
        String requiredSkills = drive.getRequiredSkills();
        String studentSkills = student.getSkills();

        if (requiredSkills != null && !requiredSkills.isBlank()) {

            if (studentSkills == null || studentSkills.isBlank()) {
                return false;
            }

            List<String> skills = Arrays.stream(studentSkills.split(","))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .toList();

            List<String> required = Arrays.stream(requiredSkills.split(","))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .toList();

            if (!skills.containsAll(required)) {
                return false;
            }
        }

        return true;
    }
}
