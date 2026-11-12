
package com.placement.dto;

import com.placement.entity.Interview;

import java.time.LocalDateTime;

public class InterviewResponse {

    private Long id;
    private Long applicationId;
    private Long studentId;
    private String studentName;
    private String companyName;
    private String jobRole;
    private String interviewRound;
    private LocalDateTime interviewDate;
    private String interviewMode;
    private String interviewLocation;
    private String status;
    private String feedback;

    public InterviewResponse(Interview interview) {

        this.id = interview.getId();

        this.applicationId = interview.getApplication().getId();

        this.studentId = interview.getApplication()
                .getStudent().getId();

        this.studentName = interview.getApplication()
                .getStudent().getUser().getFullName();

        this.companyName = interview.getApplication()
                .getPlacementDrive().getCompany().getCompanyName();

        this.jobRole = interview.getApplication()
                .getPlacementDrive().getJobRole();

        this.interviewRound = interview.getInterviewRound();
        this.interviewDate = interview.getInterviewDate();
        this.interviewMode = interview.getInterviewMode();
        this.interviewLocation = interview.getInterviewLocation();
        this.status = interview.getStatus();
        this.feedback = interview.getFeedback();
    }

    public Long getId() {
        return id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobRole() {
        return jobRole;
    }

    public String getInterviewRound() {
        return interviewRound;
    }

    public LocalDateTime getInterviewDate() {
        return interviewDate;
    }

    public String getInterviewMode() {
        return interviewMode;
    }

    public String getInterviewLocation() {
        return interviewLocation;
    }

    public String getStatus() {
        return status;
    }

    public String getFeedback() {
        return feedback;
    }
}

