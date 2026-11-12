
package com.placement.dto;

import com.placement.entity.Student;
import java.math.BigDecimal;

public class StudentResponse {

    private Long id;
    private Long userId;
    private String fullName;
    private String email;
    private String registrationNumber;
    private String branch;
    private BigDecimal cgpa;
    private Integer graduationYear;
    private String phoneNumber;
    private String resumeUrl;
    private Integer activeBacklogs;

    public StudentResponse(Student student) {
        this.id = student.getId();
        this.userId = student.getUser().getId();
        this.fullName = student.getUser().getFullName();
        this.email = student.getUser().getEmail();
        this.registrationNumber = student.getRegistrationNumber();
        this.branch = student.getBranch();
        this.cgpa = student.getCgpa();
        this.graduationYear = student.getGraduationYear();
        this.phoneNumber = student.getPhoneNumber();
        this.resumeUrl = student.getResumeUrl();
        this.activeBacklogs = student.getActiveBacklogs();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getBranch() {
        return branch;
    }

    public BigDecimal getCgpa() {
        return cgpa;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public Integer getActiveBacklogs() {
        return activeBacklogs;
    }
}

