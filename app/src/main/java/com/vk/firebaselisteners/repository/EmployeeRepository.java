package com.vk.firebaselisteners.repository;

import com.vk.firebaselisteners.callback.CallBack;
import com.vk.firebaselisteners.callback.FirebaseChildCallBack;
import com.vk.firebaselisteners.firebase.FirebaseRequestModel;
import com.vk.firebaselisteners.model.Employee;

import java.util.HashMap;

public interface EmployeeRepository {
    void createEmployee(Employee employee, CallBack callBack);

    void updateEmployee(String employeeKey, HashMap map, CallBack callBack);

    void deleteEmployee(String employeeKey, CallBack callBack);

    void readEmployeeByKey(String employeeKey, CallBack callBack);

    void readEmployeeByName(String employeeName, CallBack callBack);

    void readAllEmployeesBySingleValueEvent(CallBack callBack);

    FirebaseRequestModel readAllEmployeesByDataChangeEvent(CallBack callBack);

    FirebaseRequestModel readAllEmployeesByChildEvent(FirebaseChildCallBack firebaseChildCallBack);
}
