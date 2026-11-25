

package com.placement.service;
import com.placement.dto.AdminStudentRequest;
import com.placement.dto.StudentRequest;
import com.placement.dto.StudentResponse;
import com.placement.entity.Student;
import com.placement.entity.User;
import com.placement.repository.StudentRepository;
import com.placement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(
        StudentRepository studentRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder) {

    this.studentRepository = studentRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
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
        student.setSkills(request.getSkills());
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
        existing.setSkills(request.getSkills());
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

    // Get student profile using logged-in user's ID
    public StudentResponse getStudentByUserId(Long userId) {

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                    new RuntimeException("Student profile not found"));

        return new StudentResponse(student);
    }
    // Get all students for Admin
public java.util.List<StudentResponse> getAllStudents() {

    return studentRepository.findAll()
            .stream()
            .map(StudentResponse::new)
            .toList();
}
// Admin: Create a new student account and profile
@Transactional
public StudentResponse createStudentByAdmin(AdminStudentRequest request) {

    if (userRepository.existsByEmail(request.getEmail())) {
        throw new RuntimeException("Email already registered");
    }

    if (studentRepository.existsByRegistrationNumber(
            request.getRegistrationNumber())) {
        throw new RuntimeException("Registration number already exists");
    }
    if (request.getCgpa().compareTo(BigDecimal.ZERO) < 0 ||
        request.getCgpa().compareTo(BigDecimal.TEN) > 0) {
    throw new IllegalArgumentException(
            "CGPA must be between 0 and 10");
}

    User user = new User();
    user.setFullName(request.getFullName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole("STUDENT");

    User savedUser = userRepository.save(user);

    Student student = new Student();
    student.setUser(savedUser);
    student.setRegistrationNumber(request.getRegistrationNumber());
    student.setBranch(request.getBranch());
    student.setCgpa(request.getCgpa());
    student.setGraduationYear(request.getGraduationYear());
    student.setActiveBacklogs(0);

    Student savedStudent = studentRepository.save(student);

    return new StudentResponse(savedStudent);
}
}
