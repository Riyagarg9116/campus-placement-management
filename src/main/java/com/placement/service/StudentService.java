
package com.placement.service;

import com.placement.dto.StudentRequest;
import com.placement.dto.StudentResponse;
import com.placement.entity.Student;
import com.placement.entity.User;
import com.placement.repository.StudentRepository;
import com.placement.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentService(
            StudentRepository studentRepository,
            UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    // Create student profile
    public StudentResponse createStudent(
            Long userId, StudentRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        if (studentRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException(
                "Student profile already exists");
        }

        if (studentRepository.existsByRegistrationNumber(
                request.getRegistrationNumber())) {
            throw new RuntimeException(
                "Registration number already exists");
        }

        if (request.getCgpa().compareTo(BigDecimal.ZERO) < 0 ||
                request.getCgpa().compareTo(BigDecimal.TEN) > 0) {
            throw new RuntimeException(
                "CGPA must be between 0 and 10");
        }

        Student student = new Student();

        student.setUser(user);
        student.setRegistrationNumber(
            request.getRegistrationNumber());
        student.setBranch(request.getBranch());
        student.setCgpa(request.getCgpa());
        student.setGraduationYear(request.getGraduationYear());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setResumeUrl(request.getResumeUrl());
        student.setActiveBacklogs(request.getActiveBacklogs());

        Student savedStudent = studentRepository.save(student);

        return new StudentResponse(savedStudent);
    }

    // Get student by ID
    public StudentResponse getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Student not found"));

        return new StudentResponse(student);
    }

    // Get student by registration number
    public StudentResponse getStudentByRegistrationNumber(
            String registrationNumber) {

        Student student = studentRepository
                .findByRegistrationNumber(registrationNumber)
                .orElseThrow(() ->
                    new RuntimeException("Student not found"));

        return new StudentResponse(student);
    }

    // Update student profile
    public StudentResponse updateStudent(
            Long id, StudentRequest request) {

        Student existing = studentRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Student not found"));

        existing.setBranch(request.getBranch());
        existing.setCgpa(request.getCgpa());
        existing.setGraduationYear(request.getGraduationYear());
        existing.setPhoneNumber(request.getPhoneNumber());
        existing.setResumeUrl(request.getResumeUrl());
        existing.setActiveBacklogs(request.getActiveBacklogs());

        Student updatedStudent = studentRepository.save(existing);

        return new StudentResponse(updatedStudent);
    }
    // Get registration number using user ID
public String getRegistrationNumberByUserId(Long userId) {

    Student student = studentRepository.findByUserId(userId)
            .orElseThrow(() ->
                new RuntimeException("Student profile not found"));

    return student.getRegistrationNumber();
}
}
