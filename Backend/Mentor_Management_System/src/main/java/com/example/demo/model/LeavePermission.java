package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "holiday_permission")
public class LeavePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;

    private String studentName;

    private String reason;

    private String fromDate;

    private String toDate;

    private String status; // Pending / Approved / Rejected

    private String hostelNo;

    private String roomNo;

    public LeavePermission() {}

    public LeavePermission(Long studentId, String studentName, String reason, String fromDate, String toDate, String status, String hostelNo, String roomNo) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.reason = reason;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
        this.hostelNo = hostelNo;
        this.roomNo = roomNo;
    }

    public Long getId() { return id; }

    public Long getStudentId() { return studentId; }

    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }

    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }

    public String getFromDate() { return fromDate; }

    public void setFromDate(String fromDate) { this.fromDate = fromDate; }

    public String getToDate() { return toDate; }

    public void setToDate(String toDate) { this.toDate = toDate; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getHostelNo() { return hostelNo; }

    public void setHostelNo(String hostelNo) { this.hostelNo = hostelNo; }

    public String getRoomNo() { return roomNo; }

    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }
}