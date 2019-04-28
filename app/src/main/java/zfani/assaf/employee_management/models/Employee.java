package zfani.assaf.employee_management.models;

@SuppressWarnings("unused")

public class Employee {

    private String empId, id, name, phone;
    private long startDate;
    private boolean isSelected;

    public Employee() {

    }

    public Employee(String empId, String id, String name, String phone, long startDate) {
        this.empId = empId;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.startDate = startDate;
    }

    public String getEmpId() {
        return empId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public long getStartDate() {
        return startDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
