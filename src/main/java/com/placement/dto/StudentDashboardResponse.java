
package com.placement.dto;

public class StudentDashboardResponse {

    private long totalAvailableDrives;
    private long totalApplications;
    private long shortlistedApplications;
    private long upcomingInterviews;
    private long selectedApplications;

    public StudentDashboardResponse(
            long totalAvailableDrives,
            long totalApplications,
            long shortlistedApplications,
            long upcomingInterviews,
            long selectedApplications) {

        this.totalAvailableDrives = totalAvailableDrives;
        this.totalApplications = totalApplications;
        this.shortlistedApplications = shortlistedApplications;
        this.upcomingInterviews = upcomingInterviews;
        this.selectedApplications = selectedApplications;
    }

    public long getTotalAvailableDrives() {
        return totalAvailableDrives;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public long getShortlistedApplications() {
        return shortlistedApplications;
    }

    public long getUpcomingInterviews() {
        return upcomingInterviews;
    }

    public long getSelectedApplications() {
        return selectedApplications;
    }
}

