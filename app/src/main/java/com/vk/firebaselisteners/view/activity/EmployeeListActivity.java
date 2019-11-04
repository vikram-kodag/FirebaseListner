package com.vk.firebaselisteners.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.vk.firebaselisteners.R;
import com.vk.firebaselisteners.databinding.ActivityEmployeeListBinding;
import com.vk.firebaselisteners.viewmodel.EmployeeListViewModel;

public class EmployeeListActivity extends AppCompatActivity {
    EmployeeListViewModel employeeListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEmployeeListBinding activityEmployeeListBinding = DataBindingUtil.setContentView(this, R.layout.activity_employee_list);
        employeeListViewModel = new EmployeeListViewModel(this, activityEmployeeListBinding);
        employeeListViewModel.setActionBar();
        employeeListViewModel.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        employeeListViewModel.removeListener();
    }
}
