package com.vk.firebaselisteners.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.Map;

public class Employee implements Serializable {
    private String empName;
    private String branchName;
    private String designation;
    private String empId;
    private String empKey;
    private Salary salary;
    private Long createdDateTime;

    public Employee() {
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
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

    public void setCreatedDateTime(Long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
