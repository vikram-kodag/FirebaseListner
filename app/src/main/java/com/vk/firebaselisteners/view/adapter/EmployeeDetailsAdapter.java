package com.vk.firebaselisteners.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.vk.firebaselisteners.R;
import com.vk.firebaselisteners.databinding.EmployeeInfoRowLayoutBinding;
import com.vk.firebaselisteners.helper.IndexedLinkedHashMap;
import com.vk.firebaselisteners.model.Employee;
import com.vk.firebaselisteners.utility.Utility;
import com.vk.firebaselisteners.view.activity.AddEmployeeActivity;

import static com.vk.firebaselisteners.constants.Constant.ADD;
import static com.vk.firebaselisteners.constants.Constant.DELETE;
import static com.vk.firebaselisteners.constants.Constant.EMPLOYEE_MODEL;
import static com.vk.firebaselisteners.constants.Constant.UPDATE;

public class EmployeeDetailsAdapter extends RecyclerView.Adapter<EmployeeDetailsAdapter.EmployeeViewHolder> {
    private IndexedLinkedHashMap<String, Employee> employeeList;
    private LayoutInflater layoutInflater;
    private Activity activity;

    public EmployeeDetailsAdapter(Activity activity, IndexedLinkedHashMap<String, Employee> employeeList) {
        this.activity = activity;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        EmployeeInfoRowLayoutBinding employeeInfoRowLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.employee_info_row_layout, parent, false);
        return new EmployeeViewHolder(employeeInfoRowLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final EmployeeViewHolder holder, final int position) {
        final Employee employee = employeeList.getItemByIndex(getReversePosition(position));
        if (!Utility.isEmptyOrNull(employee.getEmpName()))
            holder.employeeInfoRowLayoutBinding.tvName.setText(employee.getEmpName());
        else
            holder.employeeInfoRowLayoutBinding.tvName.setText(R.string.na);
        if (!Utility.isEmptyOrNull(employee.getDesignation()))
            holder.employeeInfoRowLayoutBinding.tvDesignation.setText(employee.getDesignation());
        else
            holder.employeeInfoRowLayoutBinding.tvDesignation.setText(R.string.na);
        if (!Utility.isEmptyOrNull(employee.getEmpId()))
            holder.employeeInfoRowLayoutBinding.tvEmpId.setText(employee.getEmpId());
        else
            holder.employeeInfoRowLayoutBinding.tvEmpId.setText(R.string.na);
        if (null != employee.getBranchDetails() && !Utility.isEmptyOrNull(employee.getBranchDetails().getBranchName()))
            holder.employeeInfoRowLayoutBinding.tvBranch.setText(employee.getBranchDetails().getBranchName());
        else
            holder.employeeInfoRowLayoutBinding.tvBranch.setText(R.string.na);
    }


    @Override
    public int getItemCount() {
        if (employeeList != null)
            return employeeList.size();
        else return 0;
    }

    private int getReversePosition(int index) {
        if (employeeList != null && !employeeList.isEmpty())
            return employeeList.size() - 1 - index;
        else return 0;
    }

    public void reloadList(IndexedLinkedHashMap<String, Employee> list) {
        employeeList = list;
        notifyDataSetChanged();
    }

    public void reloadList(int index, String operation) {
        switch (operation) {
            case ADD:
                notifyItemInserted(getReversePosition(index));
                break;
            case UPDATE:
                notifyItemChanged(getReversePosition(index));
                break;
            case DELETE:
                notifyItemRemoved(getReversePosition(index));
                break;
            default:
                notifyDataSetChanged();
                break;
        }
    }


    public class EmployeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private EmployeeInfoRowLayoutBinding employeeInfoRowLayoutBinding;

        private EmployeeViewHolder(EmployeeInfoRowLayoutBinding employeeInfoRowLayoutBinding) {
            super(employeeInfoRowLayoutBinding.getRoot());
            this.employeeInfoRowLayoutBinding = employeeInfoRowLayoutBinding;
            employeeInfoRowLayoutBinding.cardViewRowClick.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, AddEmployeeActivity.class);
            intent.putExtra(EMPLOYEE_MODEL, employeeList.getItemByIndex(getReversePosition(getAdapterPosition())));
            activity.startActivity(intent);
        }
    }//end of view holder

    public IndexedLinkedHashMap<String, Employee> getEmployeeList() {
        return employeeList;
    }
}
