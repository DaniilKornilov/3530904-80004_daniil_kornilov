package main.web;

import main.entity.Department;
import main.entity.DepartmentEmployee;
import main.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DepartmentEmployeeController {
    @Autowired
    RestController restController;

    @RequestMapping("/depEmps/main/{err}")
    public String showDepEmp(@PathVariable String err, Model model) {
        model.addAttribute("error", err);
        return "DepartmentsEmployees";
    }

    @RequestMapping("/depEmps/all")
    public String allDepEmps(Model model) {
        List<DepartmentEmployee> list = restController.getAllDepartmentsEmployees().getBody();
        model.addAttribute("list", list);
        return "DepartmentsEmployees";
    }

    @RequestMapping("/depEmps/{id}")
    public String depEmpById(Model model, @PathVariable String id) {
        try {
            Integer.parseInt(id);
        } catch (Exception exception) {
            model.addAttribute("err", -2);
            return "DepartmentsEmployees";
        }
        DepartmentEmployee depEmp = restController.getDepEmp(Integer.parseInt(id)).getBody();
        if (depEmp == null) {
            model.addAttribute("err", -1);
            return "DepartmentsEmployees";
        }
        model.addAttribute("depEmp", depEmp);
        return "DepartmentEmployeeID";
    }

    @RequestMapping("/depEmps/delete/{id}")
    public String deleteDepEmpById(@PathVariable String id, Model model) {
        try {
            Integer.parseInt(id);
        } catch (Exception exception) {
            model.addAttribute("err", -2);
            return "DepartmentsEmployees";
        }
        int err = restController.deleteDepEmp(Integer.parseInt(id));
        model.addAttribute("err", err);
        return "DepartmentsEmployees";
    }

    @RequestMapping("/depEmps/delete/")
    public String deleteDepEmpById(Model model) {
        restController.deleteAllDepEmp();
        model.addAttribute("err", 0);
        return "Employees";
    }

    @RequestMapping("/depEmps/update/{id}")
    public String updateDepEmpById(
            @PathVariable String id,
            @RequestParam(name = "depId", required = false, defaultValue = "") String depId,
            @RequestParam(name = "empId", required = false, defaultValue = "") String empId,
            Model model
    ) {
        try {
            Department dep = restController.getDepartment(Integer.parseInt(depId)).getBody();
            Employee emp = restController.getEmployee(Integer.parseInt(empId)).getBody();
            if (emp == null) {
                model.addAttribute("err", -6);
                return "DepartmentsEmployees";
            }
            if (dep == null) {
                model.addAttribute("err", -5);
                return "DepartmentsEmployees";
            }
        } catch (Exception e) {
            model.addAttribute("err", -2);
            return "DepartmentsEmployees";
        }

        try {
            Integer.parseInt(id);
        } catch (Exception exception) {
            model.addAttribute("err", -2);
            return "DepartmentsEmployees";
        }
        try {
            Integer.parseInt(depId);
            Integer.parseInt(empId);
        } catch (Exception e) {
            model.addAttribute("err", -3);
            return "DepartmentsEmployees";
        }
        DepartmentEmployee oldDepEmp = restController.getDepEmp(Integer.parseInt(id)).getBody();
        if (oldDepEmp == null) {
            model.addAttribute("err", -1);
            return "DepartmentsEmployees";
        }
        if (depId.equals("")) {
            depId = oldDepEmp.getDepartmentId().toString();
        }
        if (empId.equals("")) {
            empId = oldDepEmp.getEmployeeId().toString();
        }
        int err = restController.updateDepEmp(Integer.parseInt(id), depId, empId);
        model.addAttribute("err", err);
        return "DepartmentsEmployees";
    }

    @RequestMapping("/depEmps/add")
    public String addDepEmp(
            @RequestParam(name = "depId", required = false, defaultValue = "") String depId,
            @RequestParam(name = "empId", required = false, defaultValue = "") String empId,
            Model model
    ) {
        try {
            Department dep = restController.getDepartment(Integer.parseInt(depId)).getBody();
            Employee emp = restController.getEmployee(Integer.parseInt(empId)).getBody();
            if (emp == null) {
                model.addAttribute("err", -6);
                return "DepartmentsEmployees";
            }
            if (dep == null) {
                model.addAttribute("err", -5);
                return "DepartmentsEmployees";
            }
        } catch (Exception e) {
            model.addAttribute("err", -2);
            return "DepartmentsEmployees";
        }
        try {
            Integer.parseInt(depId);
            Integer.parseInt(empId);
        } catch (Exception e) {
            model.addAttribute("err", -3);
            return "DepartmentsEmployees";
        }
        restController.addDepartment(depId, empId);
        model.addAttribute("err", 0);
        return "DepartmentsEmployees";
    }
}
