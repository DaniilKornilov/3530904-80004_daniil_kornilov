package main.entity;

import javax.persistence.*;

@Entity
public class DepartmentEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "employee_id")
    private Integer employeeId;

    public DepartmentEmployee() {
    }

    public DepartmentEmployee(Integer dId, Integer eId) {
        this.departmentId = dId;
        this.employeeId = eId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "DepartmentEmployee{" +
                "id=" + id +
                ", departmentId=" + departmentId +
                ", employeeId=" + employeeId +
                '}';
    }
}
