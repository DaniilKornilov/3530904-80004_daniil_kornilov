package main.service;

import main.entity.Department;
import main.exeptions.EntityNotFoundException;
import main.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository depRepo;

    @Override
    public List<Department> listDepartments() {
        return (List<Department>) depRepo.findAll();
    }

    @Override
    public Department getDepartment(Integer id) {
        Optional<Department> optionalDep = depRepo.findById(id);
        if (optionalDep.isPresent()) {
            return optionalDep.get();
        } else {
            throw new EntityNotFoundException("Department not found");
        }
    }

    @Override
    public Department addDepartment(Department department) {
        return depRepo.save(department);
    }

    @Override
    public void deleteAllDepartments() {
        depRepo.deleteAll();
    }

    @Override
    public void deleteDepartment(int id) {
        depRepo.deleteById(id);
    }


}
