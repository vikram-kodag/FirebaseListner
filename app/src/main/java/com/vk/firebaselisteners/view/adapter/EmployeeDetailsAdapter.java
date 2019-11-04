package com.vk.firebaselisteners.view.adapter;

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

import static com.vk.firebaselisteners.constants.Constant.ADD;
import static com.vk.firebaselisteners.constants.Constant.DELETE;
import static com.vk.firebaselisteners.constants.Constant.UPDATE;

public class EmployeeDetailsAdapter extends RecyclerView.Adapter<EmployeeDetailsAdapter.EmployeeViewHolder> {
    private IndexedLinkedHashMap<String, Employee> employeeList;
    private LayoutInflater layoutInflater;

    public EmployeeDetailsAdapter(IndexedLinkedHashMap<String, Employee> employeeList) {
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
        final Employee employee = employeeList.getItemByIndex(position);
        if (!Utility.isEmptyOrNull(employee.getEmpName()))
            holder.employeeInfoRowLayoutBinding.tvName.setText(employee.getBranchName());
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
        if (!Utility.isEmptyOrNull(employee.getBranchName()))
            holder.employeeInfoRowLayoutBinding.tvBranch.setText(employee.getBranchName());
        else
            holder.employeeInfoRowLayoutBinding.tvBranch.setText(R.string.na);
    }


    @Override
    public int getItemCount() {
        if (employeeList != null)
            return employeeList.size();
        else return 0;
    }

    public void reloadList(IndexedLinkedHashMap<String, Employee> list) {
        employeeList = list;
        notifyDataSetChanged();
    }

    public void reloadList(int index, String operation) {
        switch (operation) {
            case ADD:
                notifyItemInserted(index);
                break;
            case UPDATE:
                notifyItemChanged(index);
                break;
            case DELETE:
                notifyItemRemoved(index);
                break;
            default:
                notifyDataSetChanged();
                break;
        }
        notifyDataSetChanged();
    }


    public class EmployeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private EmployeeInfoRowLayoutBinding employeeInfoRowLayoutBinding;

        private EmployeeViewHolder(EmployeeInfoRowLayoutBinding employeeInfoRowLayoutBinding) {
            super(employeeInfoRowLayoutBinding.getRoot());
            this.employeeInfoRowLayoutBinding = employeeInfoRowLayoutBinding;
        }

        @Override
        public void onClick(View v) {
        }
    }//end of view holder

    public IndexedLinkedHashMap<String, Employee> getEmployeeList() {
        return employeeList;
    }
}
