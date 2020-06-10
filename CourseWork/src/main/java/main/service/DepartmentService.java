package main.service;

import main.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> listDepartments();

    Department getDepartment(Integer id);

    Department addDepartment(Department department);

    void deleteAllDepartments();

    void deleteDepartment(int id);
}
