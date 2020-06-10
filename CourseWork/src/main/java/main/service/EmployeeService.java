package main.service;

import main.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> listEmployees();

    Employee getEmployees(Integer id);

    void addEmployees(Employee emp);

    void deleteAllEmployees();

    void deleteEmployee(int id);
}
