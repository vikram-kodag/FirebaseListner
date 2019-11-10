package com.vk.firebaselisteners.viewmodel;


import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vk.firebaselisteners.R;
import com.vk.firebaselisteners.callback.FirebaseChildCallBack;
import com.vk.firebaselisteners.databinding.ActivityEmployeeListBinding;
import com.vk.firebaselisteners.firebase.FirebaseRequestModel;
import com.vk.firebaselisteners.firebase.FirebaseUtility;
import com.vk.firebaselisteners.helper.IndexedLinkedHashMap;
import com.vk.firebaselisteners.model.Employee;
import com.vk.firebaselisteners.repository.EmployeeRepository;
import com.vk.firebaselisteners.repository.impl.EmployeeRepositoryImpl;
import com.vk.firebaselisteners.utility.Utility;
import com.vk.firebaselisteners.view.activity.AddEmployeeActivity;
import com.vk.firebaselisteners.view.activity.EmployeeListActivity;
import com.vk.firebaselisteners.view.adapter.EmployeeDetailsAdapter;

import static com.vk.firebaselisteners.constants.Constant.ADD;
import static com.vk.firebaselisteners.constants.Constant.DELETE;
import static com.vk.firebaselisteners.constants.Constant.UPDATE;


public class EmployeeListViewModel {
    private ActivityEmployeeListBinding activityEmployeeListBinding;
    private EmployeeListActivity employeeListActivity;
    private EmployeeDetailsAdapter employeeDetailsAdapter;
    private EmployeeRepository employeeRepository;
    private FirebaseRequestModel firebaseRequestModel;

    public EmployeeListViewModel(EmployeeListActivity employeeListActivity, ActivityEmployeeListBinding activityEmployeeListBinding) {
        this.activityEmployeeListBinding = activityEmployeeListBinding;
        this.employeeListActivity = employeeListActivity;
        employeeRepository = new EmployeeRepositoryImpl(employeeListActivity);
    }

    public void setActionBar() {
        Toolbar tb = activityEmployeeListBinding.toolbar;
        employeeListActivity.setSupportActionBar(tb);
        ActionBar actionBar = employeeListActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.app_name);
        }
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employeeListActivity.onBackPressed();
            }
        });
    }

    public void init() {
        getAllEmployees();
    }

    private void setAdapter(IndexedLinkedHashMap<String, Employee> employeeIndexedLinkedHashMap) {
        if (employeeDetailsAdapter == null) {
            employeeDetailsAdapter = new EmployeeDetailsAdapter(employeeListActivity, employeeIndexedLinkedHashMap);
            activityEmployeeListBinding.rvEmployeeList.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(employeeListActivity);
            activityEmployeeListBinding.rvEmployeeList.setLayoutManager(mLayoutManager);
            activityEmployeeListBinding.rvEmployeeList.setItemAnimator(new DefaultItemAnimator());
            activityEmployeeListBinding.rvEmployeeList.setAdapter(employeeDetailsAdapter);
        } else {
            employeeDetailsAdapter.reloadList(employeeIndexedLinkedHashMap);
        }
    }

    private void getAllEmployees() {
        if (firebaseRequestModel != null)
            removeListener();
        firebaseRequestModel = employeeRepository.readAllEmployeesByChildEvent(new FirebaseChildCallBack() {
            @Override
            public void onChildAdded(Object object) {
                if (object != null) {
                    Employee employee = (Employee) object;
                    if (employeeDetailsAdapter == null)
                        setAdapter(new IndexedLinkedHashMap<String, Employee>());
                    employeeDetailsAdapter.getEmployeeList().add(employee.getEmpKey(), employee);
                    employeeDetailsAdapter.reloadList(employeeDetailsAdapter.getEmployeeList().size() - 1, ADD);
                }
            }

            @Override
            public void onChildChanged(Object object) {
                if (object != null) {
                    Employee employee = (Employee) object;
                    employeeDetailsAdapter.getEmployeeList().update(employee.getEmpKey(), employee);
                    employeeDetailsAdapter.reloadList(employeeDetailsAdapter.getEmployeeList().getIndexByKey(employee.getEmpKey()), UPDATE);
                }
            }

            @Override
            public void onChildRemoved(Object object) {
                if (object != null) {
                    Employee employee = (Employee) object;
                    employeeDetailsAdapter.getEmployeeList().update(employee.getEmpKey(), employee);
                    employeeDetailsAdapter.reloadList(employeeDetailsAdapter.getEmployeeList().getIndexByKey(employee.getEmpKey()), DELETE);
                }
            }

            @Override
            public void onChildMoved(Object object) {

            }

            @Override
            public void onCancelled(Object object) {
                Utility.showMessage(employeeListActivity, employeeListActivity.getString(R.string.some_thing_went_wrong));
            }
        });
    }

    public void removeListener() {
        FirebaseUtility.removeFireBaseChildListener(employeeListActivity, firebaseRequestModel);
    }

    public void setFabClickListener() {
        activityEmployeeListBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeListActivity.startActivity(new Intent(employeeListActivity, AddEmployeeActivity.class));
            }
        });
    }
}
