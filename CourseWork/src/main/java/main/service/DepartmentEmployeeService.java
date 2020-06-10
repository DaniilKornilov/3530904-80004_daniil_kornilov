package main.service;

import main.entity.DepartmentEmployee;

import java.util.List;

public interface DepartmentEmployeeService {
    List<DepartmentEmployee> listDepEmp();

    DepartmentEmployee getDepEmp(Integer id);

    void addDepEmp(DepartmentEmployee depEmp);

    void deleteAllDepEmp();

    void deleteDepEmp(int id);
}
