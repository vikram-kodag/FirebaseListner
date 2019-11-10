package com.vk.firebaselisteners.repository.impl;

import android.app.Activity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vk.firebaselisteners.R;
import com.vk.firebaselisteners.callback.CallBack;
import com.vk.firebaselisteners.callback.FirebaseChildCallBack;
import com.vk.firebaselisteners.firebase.FirebaseRepository;
import com.vk.firebaselisteners.firebase.FirebaseRequestModel;
import com.vk.firebaselisteners.model.Employee;
import com.vk.firebaselisteners.repository.EmployeeRepository;
import com.vk.firebaselisteners.utility.Utility;
import com.vk.firebaselisteners.view.dialog.ProgressDialogClass;

import java.util.ArrayList;
import java.util.HashMap;

import static com.vk.firebaselisteners.constants.Constant.FAIL;
import static com.vk.firebaselisteners.constants.Constant.SUCCESS;
import static com.vk.firebaselisteners.firebase.FirebaseConstants.EMPLOYEE_TABLE;
import static com.vk.firebaselisteners.firebase.FirebaseDatabaseReference.DATABASE;

public class EmployeeRepositoryImpl extends FirebaseRepository implements EmployeeRepository {
    private ProgressDialogClass progressDialog;
    private Activity activity;
    private DatabaseReference employeeDatabaseReference;

    public EmployeeRepositoryImpl(Activity activity) {
        this.activity = activity;
        progressDialog = new ProgressDialogClass(activity);
        employeeDatabaseReference = DATABASE.getReference(EMPLOYEE_TABLE);
    }

    @Override
    public void createEmployee(Employee employee, final CallBack callBack) {
        String pushKey = employeeDatabaseReference.push().getKey();
        if (employee != null && !Utility.isEmptyOrNull(pushKey)) {
            progressDialog.showDialog(getString(R.string.loading), getString(R.string.please_wait));
            employee.setEmpKey(pushKey);
            DatabaseReference databaseReference = employeeDatabaseReference.child(pushKey);
            fireBaseCreate(databaseReference, employee, new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    progressDialog.dismissDialog();
                    callBack.onSuccess(SUCCESS);
                }

                @Override
                public void onError(Object object) {
                    progressDialog.dismissDialog();
                    callBack.onError(object);
                }
            });
        } else {
            callBack.onError(FAIL);
        }
    }

    @Override
    public void updateEmployee(String employeeKey, HashMap map, final CallBack callBack) {
        if (!Utility.isEmptyOrNull(employeeKey)) {
            progressDialog.showDialog(getString(R.string.loading), getString(R.string.please_wait));
            DatabaseReference databaseReference = employeeDatabaseReference.child(employeeKey);
            fireBaseUpdateChildren(databaseReference, map, new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    progressDialog.dismissDialog();
                    callBack.onSuccess(SUCCESS);
                }

                @Override
                public void onError(Object object) {
                    progressDialog.dismissDialog();
                    callBack.onError(object);
                }
            });
        } else {
            callBack.onError(FAIL);
        }
    }

    @Override
    public void deleteEmployee(String employeeKey, final CallBack callBack) {
        if (!Utility.isEmptyOrNull(employeeKey)) {
            progressDialog.showDialog(getString(R.string.loading), getString(R.string.please_wait));
            DatabaseReference databaseReference = employeeDatabaseReference.child(employeeKey);
            fireBaseDelete(databaseReference, new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    progressDialog.dismissDialog();
                    callBack.onSuccess(SUCCESS);
                }

                @Override
                public void onError(Object object) {
                    progressDialog.dismissDialog();
                    callBack.onError(object);
                }
            });
        } else {
            callBack.onError(FAIL);
        }
    }

    @Override
    public void readEmployeeByKey(String employeeKey, final CallBack callBack) {
        if (!Utility.isEmptyOrNull(employeeKey)) {
            progressDialog.showDialog(getString(R.string.loading), getString(R.string.please_wait));
            Query query = employeeDatabaseReference.child(employeeKey);
            fireBaseReadData(query, new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        DataSnapshot dataSnapshot = (DataSnapshot) object;
                        if (dataSnapshot.getValue() != null && dataSnapshot.hasChildren()) {
                            Employee employee = dataSnapshot.getValue(Employee.class);
                            callBack.onSuccess(employee);
                        } else
                            callBack.onSuccess(null);
                    } else
                        callBack.onSuccess(null);
                    progressDialog.dismissDialog();
                }

                @Override
                public void onError(Object object) {
                    progressDialog.dismissDialog();
                    callBack.onError(object);
                }
            });
        } else {
            callBack.onError(FAIL);
        }
    }

    @Override
    public void readEmployeeByName(String employeeName, final CallBack callBack) {
        if (!Utility.isEmptyOrNull(employeeName)) {
            progressDialog.showDialog(getString(R.string.loading), getString(R.string.please_wait));
            Query query = employeeDatabaseReference.orderByChild("empName").equalTo(employeeName);
            fireBaseReadData(query, new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        DataSnapshot dataSnapshot = (DataSnapshot) object;
                        /*
                         *Here we assume that empName is unique
                         * else the parsing technique will be different
                         */
                        if (dataSnapshot.getValue() != null && dataSnapshot.hasChildren()) {
                            DataSnapshot child = dataSnapshot.getChildren().iterator().next();
                            Employee employee = child.getValue(Employee.class);
                            callBack.onSuccess(employee);
                        } else
                            callBack.onSuccess(null);
                    } else
                        callBack.onSuccess(null);
                    progressDialog.dismissDialog();
                }

                @Override
                public void onError(Object object) {
                    progressDialog.dismissDialog();
                    callBack.onError(object);
                }
            });
        } else {
            callBack.onError(FAIL);
        }
    }

    @Override
    public void readAllEmployeesBySingleValueEvent(final CallBack callBack) {
        progressDialog.showDialog(getString(R.string.loading), getString(R.string.please_wait));
        //get all employees order by employee name
        Query query = employeeDatabaseReference.orderByChild("empName");
        fireBaseReadData(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null && dataSnapshot.hasChildren()) {
                        ArrayList<Employee> employeeArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            Employee employee = suggestionSnapshot.getValue(Employee.class);
                            employeeArrayList.add(employee);
                        }
                        callBack.onSuccess(employeeArrayList);
                    } else
                        callBack.onSuccess(null);
                } else
                    callBack.onSuccess(null);
                progressDialog.dismissDialog();
            }

            @Override
            public void onError(Object object) {
                progressDialog.dismissDialog();
                callBack.onError(object);
            }
        });
    }

    @Override
    public FirebaseRequestModel readAllEmployeesByDataChangeEvent(final CallBack callBack) {
        progressDialog.showDialog(getString(R.string.loading), getString(R.string.please_wait));
        //get all employees order by employee name
        Query query = employeeDatabaseReference.orderByChild("empName");
        ValueEventListener valueEventListener = fireBaseDataChangeListener(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null && dataSnapshot.hasChildren()) {
                        ArrayList<Employee> employeeArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            Employee employee = suggestionSnapshot.getValue(Employee.class);
                            employeeArrayList.add(employee);
                        }
                        callBack.onSuccess(employeeArrayList);
                    } else
                        callBack.onSuccess(null);
                } else
                    callBack.onSuccess(null);
                progressDialog.dismissDialog();
            }

            @Override
            public void onError(Object object) {
                progressDialog.dismissDialog();
                callBack.onError(object);
            }
        });
        return new FirebaseRequestModel(valueEventListener, query);
    }

    @Override
    public FirebaseRequestModel readAllEmployeesByChildEvent(final FirebaseChildCallBack firebaseChildCallBack) {
        progressDialog.showDialog(getString(R.string.loading), getString(R.string.please_wait));
        //get all employees order by created date time
        Query query = employeeDatabaseReference.orderByChild("createdDateTime");
        ChildEventListener childEventListener = fireBaseChildEventListener(query, new FirebaseChildCallBack() {
                    @Override
                    public void onChildAdded(Object object) {
                        DataSnapshot dataSnapshot = (DataSnapshot) object;
                        if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                            Employee employee = dataSnapshot.getValue(Employee.class);
                            firebaseChildCallBack.onChildAdded(employee);
                        }
                        progressDialog.dismissDialog();
                    }

                    @Override
                    public void onChildChanged(Object object) {
                        DataSnapshot dataSnapshot = (DataSnapshot) object;
                        if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                            Employee employee = dataSnapshot.getValue(Employee.class);
                            firebaseChildCallBack.onChildChanged(employee);
                        }
                    }

                    @Override
                    public void onChildRemoved(Object object) {
                        DataSnapshot dataSnapshot = (DataSnapshot) object;
                        if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                            Employee employee = dataSnapshot.getValue(Employee.class);
                            firebaseChildCallBack.onChildRemoved(employee);
                        }
                        progressDialog.dismissDialog();
                    }

                    @Override
                    public void onChildMoved(Object object) {
                        DataSnapshot dataSnapshot = (DataSnapshot) object;
                        if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                            Employee employee = dataSnapshot.getValue(Employee.class);
                            firebaseChildCallBack.onChildMoved(employee);
                        }
                        progressDialog.dismissDialog();
                    }

                    @Override
                    public void onCancelled(Object object) {
                        DataSnapshot dataSnapshot = (DataSnapshot) object;
                        if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                            Employee employee = dataSnapshot.getValue(Employee.class);
                            firebaseChildCallBack.onCancelled(employee);
                        }
                        progressDialog.dismissDialog();
                    }
                }
        );
        query.addChildEventListener(childEventListener);
        return new FirebaseRequestModel(childEventListener, query);

    }

    private String getString(int id) {
        return activity.getString(id);
    }
}
