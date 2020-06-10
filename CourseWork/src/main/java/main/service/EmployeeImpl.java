package main.service;

import main.entity.Employee;
import main.exeptions.EntityNotFoundException;
import main.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo empRepo;

    @Override
    public List<Employee> listEmployees() {
        return (List<Employee>) empRepo.findAll();
    }

    @Override
    public Employee getEmployees(Integer id) {
        Optional<Employee> optionalEmp = empRepo.findById(id);
        if (optionalEmp.isPresent()) {
            return optionalEmp.get();
        } else {
            throw new EntityNotFoundException("Employee not found");
        }
    }

    @Override
    public void addEmployees(Employee emp) {
        empRepo.save(emp);
    }

    @Override
    public void deleteAllEmployees() {
        empRepo.deleteAll();
    }

    @Override
    public void deleteEmployee(int id) {
        empRepo.deleteById(id);
    }
}
