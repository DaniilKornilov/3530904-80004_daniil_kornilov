package main.service;

import main.entity.DepartmentEmployee;
import main.exeptions.EntityNotFoundException;
import main.repository.DepartmentEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentEmployeeImpl implements DepartmentEmployeeService {

    @Autowired
    private DepartmentEmployeeRepository depEmpRepo;

    @Override
    public List<DepartmentEmployee> listDepEmp() {
        return (List<DepartmentEmployee>) depEmpRepo.findAll();
    }

    @Override
    public DepartmentEmployee getDepEmp(Integer id) {
        Optional<DepartmentEmployee> optionalDepEmp = depEmpRepo.findById(id);
        if (optionalDepEmp.isPresent()) {
            return optionalDepEmp.get();
        } else {
            throw new EntityNotFoundException("Departments_Employees not found");
        }
    }

    @Override
    public void addDepEmp(DepartmentEmployee depEmp) {
        depEmpRepo.save(depEmp);
    }

    @Override
    public void deleteAllDepEmp() {
        depEmpRepo.deleteAll();
    }

    @Override
    public void deleteDepEmp(int id) {
        depEmpRepo.deleteById(id);
    }
}
