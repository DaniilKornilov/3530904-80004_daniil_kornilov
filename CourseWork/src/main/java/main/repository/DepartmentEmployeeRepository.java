package main.repository;

import main.entity.DepartmentEmployee;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentEmployeeRepository extends CrudRepository<DepartmentEmployee, Integer> {
}
