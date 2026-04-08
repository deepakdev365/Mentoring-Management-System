package com.example.demo.dto;

public class DashboardSummaryDTO {
    private Double attendancePercentage;
    private Double performancePercentage; // Average marks %
    private String paymentStatus;
    private String enrollmentStatus;
    private Integer activeCourses;

    // Constructors
    public DashboardSummaryDTO() {}

    public DashboardSummaryDTO(Double attendancePercentage, Double performancePercentage, 
                               String paymentStatus, String enrollmentStatus, Integer activeCourses) {
        this.attendancePercentage = attendancePercentage;
        this.performancePercentage = performancePercentage;
        this.paymentStatus = paymentStatus;
        this.enrollmentStatus = enrollmentStatus;
        this.activeCourses = activeCourses;
    }

    // Getters and Setters
    public Double getAttendancePercentage() { return attendancePercentage; }
    public void setAttendancePercentage(Double attendancePercentage) { this.attendancePercentage = attendancePercentage; }

    public Double getPerformancePercentage() { return performancePercentage; }
    public void setPerformancePercentage(Double performancePercentage) { this.performancePercentage = performancePercentage; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getEnrollmentStatus() { return enrollmentStatus; }
    public void setEnrollmentStatus(String enrollmentStatus) { this.enrollmentStatus = enrollmentStatus; }

    public Integer getActiveCourses() { return activeCourses; }
    public void setActiveCourses(Integer activeCourses) { this.activeCourses = activeCourses; }
}
