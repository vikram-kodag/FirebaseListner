package com.vk.firebaselisteners.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Employee implements Serializable {
    private String empName;
    private String designation;
    private String empId;
    private String empKey;
    private Long createdDateTime;
    private Long updatedDateTime;
    private BranchDetails branchDetails;

    public Employee() {
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public BranchDetails getBranchDetails() {
        return branchDetails;
    }

    public void setBranchDetails(BranchDetails branchDetails) {
        this.branchDetails = branchDetails;
    }

    public String getEmpKey() {
        return empKey;
    }

    public void setEmpKey(String empKey) {
        this.empKey = empKey;
    }

    @Exclude
    public Long getCreatedDateTimeLong() {
        return createdDateTime;
    }

    public Map<String, String> getCreatedDateTime() {
        return ServerValue.TIMESTAMP;
    }

    public Map<String, String> getUpdatedDateTime() {
        return ServerValue.TIMESTAMP;
    }

    public Long getUpdatedDateTimeLong() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Long updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public void setCreatedDateTime(Long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public HashMap<String, Object> getMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("empName", getEmpName());
        map.put("designation", getDesignation());
        map.put("branchDetails", getBranchDetails());
        map.put("updatedDateTime", getUpdatedDateTime());
        return map;
    }
}
