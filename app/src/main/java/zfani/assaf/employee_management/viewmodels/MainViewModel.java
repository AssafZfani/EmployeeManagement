package zfani.assaf.employee_management.viewmodels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import zfani.assaf.employee_management.models.Employee;

public class MainViewModel extends AndroidViewModel {

    private final List<Employee> employeeList;
    private final MutableLiveData<Boolean> hasEmployeeSelected;
    private final DatabaseReference reference;

    public MainViewModel(@NonNull Application application) {
        super(application);
        employeeList = new ArrayList<>();
        hasEmployeeSelected = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("Employees");
    }

    public MutableLiveData<Boolean> hasEmployeeSelected() {
        return hasEmployeeSelected;
    }

    public void addOrRemoveEmployee(Employee employee) {
        if (employeeList.contains(employee)) {
            employeeList.remove(employee);
        } else {
            employeeList.add(employee);
        }
        hasEmployeeSelected.setValue(!employeeList.isEmpty());
    }

    public Employee getSelectedEmployee() {
        return employeeList.get(employeeList.size() - 1);
    }

    public void addOrUpdateEmployee(boolean isEdit, String id, String name, String phone, long date) {
        String empId = isEdit ? getSelectedEmployee().getEmpId() : null;
        DatabaseReference ref = isEdit ? reference.child(empId) : reference.push();
        String toastMessage = isEdit ? "פרטי העובד עודכנו בהצלחה!" : "העובד נוסף בהצלחה!";
        ref.setValue(new Employee(isEdit ? empId : ref.getKey(), id, name, phone, date));
        clearEmployeeList();
        Toast.makeText(getApplication(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    public void deleteEmployees() {
        for (Employee employee : employeeList) {
            reference.child(employee.getEmpId()).removeValue();
        }
        clearEmployeeList();
        Toast.makeText(getApplication(), "העובד/ים נמחק/ו בהצלחה!", Toast.LENGTH_SHORT).show();
    }

    public void clearEmployeeList() {
        employeeList.clear();
        hasEmployeeSelected.setValue(false);
    }
}
