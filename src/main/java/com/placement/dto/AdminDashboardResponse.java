
package com.placement.dto;

public class AdminDashboardResponse {

    private long totalStudents;
    private long totalCompanies;
    private long totalDrives;
    private long totalApplications;
    private long totalSelectedStudents;

    public AdminDashboardResponse(
            long totalStudents,
            long totalCompanies,
            long totalDrives,
            long totalApplications,
            long totalSelectedStudents) {

        this.totalStudents = totalStudents;
        this.totalCompanies = totalCompanies;
        this.totalDrives = totalDrives;
        this.totalApplications = totalApplications;
        this.totalSelectedStudents = totalSelectedStudents;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public long getTotalCompanies() {
        return totalCompanies;
    }

    public long getTotalDrives() {
        return totalDrives;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public long getTotalSelectedStudents() {
        return totalSelectedStudents;
    }
}

