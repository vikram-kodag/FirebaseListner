package com.vk.firebaselisteners.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.vk.firebaselisteners.R;
import com.vk.firebaselisteners.databinding.ActivityAddEmployeeBinding;
import com.vk.firebaselisteners.viewmodel.AddEmployeeViewModel;

public class AddEmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddEmployeeBinding activityAddEmployeeBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_employee);
        AddEmployeeViewModel addEmployeeViewModel = new AddEmployeeViewModel(this, activityAddEmployeeBinding);
        addEmployeeViewModel.setActionBar();
        addEmployeeViewModel.initView();
        addEmployeeViewModel.addClickListener();
    }
}
