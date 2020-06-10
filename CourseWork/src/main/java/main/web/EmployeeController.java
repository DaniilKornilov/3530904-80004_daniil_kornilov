package main.web;

import main.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    RestController restController;

    @RequestMapping("/employees/main/{err}")
    public String showEmp(@PathVariable String err, Model model) {
        model.addAttribute("error", err);
        return "Employees";
    }

    @RequestMapping("/employees/all")
    public String allEmployees(Model model) {
        List<Employee> list = restController.getAllEmployees().getBody();
        model.addAttribute("list", list);
        return "Employees";
    }

    @RequestMapping("/employees/{id}")
    public String employeeById(Model model, @PathVariable String id) {
        try {
            Integer.parseInt(id);
        } catch (Exception exception) {
            model.addAttribute("err", -2);
            return "Employees";
        }
        Employee emp = restController.getEmployee(Integer.parseInt(id)).getBody();
        if (emp == null) {
            model.addAttribute("err", -1);
            return "Employees";
        }
        model.addAttribute("employee", emp);
        return "EmployeeID";
    }

    @RequestMapping("/employees/delete/{id}")
    public String deleteEmpById(@PathVariable String id, Model model) {
        try {
            Integer.parseInt(id);
        } catch (Exception exception) {
            model.addAttribute("err", -2);
            return "Employees";
        }
        int err = restController.deleteEmployee(Integer.parseInt(id));
        model.addAttribute("err", err);
        return "Employees";
    }

    @RequestMapping("/employees/delete/")
    public String deleteAllEmp(Model model) {
        restController.deleteAllEmployees();
        model.addAttribute("err", 0);
        return "Employees";
    }

    @RequestMapping("/employees/update/{id}")
    public String updateEmpById(
            @PathVariable String id,
            @RequestParam(name = "fName", required = false, defaultValue = "") String fName,
            @RequestParam(name = "lName", required = false, defaultValue = "") String lName,
            @RequestParam(name = "pName", required = false, defaultValue = "") String pName,
            @RequestParam(name = "position", required = false, defaultValue = "") String position,
            @RequestParam(name = "salary", required = false, defaultValue = "") String salary, Model model
    ) {
        try {
            Integer.parseInt(id);
        } catch (Exception exception) {
            model.addAttribute("err", -2);
            return "Employees";
        }
        Employee oldEmp = restController.getEmployee(Integer.parseInt(id)).getBody();
        if (oldEmp == null) {
            model.addAttribute("err", -1);
            return "Employees";
        }
        if (fName.equals("")) {
            fName = oldEmp.getFirstName();
        }
        if (lName.equals("")) {
            lName = oldEmp.getLastName();
        }
        if (pName.equals("")) {
            pName = oldEmp.getFatherName();
        }
        if (position.equals("")) {
            position = oldEmp.getPosition();
        }
        if (salary.equals("")) {
            salary = oldEmp.getSalary().toString();
        }
        try {
            Double.parseDouble(salary);
        } catch (Exception exception) {
            model.addAttribute("err", -3);
            return "Employees";
        }
        int err = restController.updateEmployee(Integer.parseInt(id), fName, lName, pName, position, salary);
        model.addAttribute("err", err);
        return "Employees";
    }

    @RequestMapping("/employees/add")
    public String addEmp(
            @RequestParam(name = "fName", required = false, defaultValue = "") String fName,
            @RequestParam(name = "lName", required = false, defaultValue = "") String lName,
            @RequestParam(name = "pName", required = false, defaultValue = "") String pName,
            @RequestParam(name = "position", required = false, defaultValue = "") String position,
            @RequestParam(name = "salary", required = false, defaultValue = "") String salary, Model model
    ) {
        try {
            Double.parseDouble(salary);
        } catch (Exception exception) {
            model.addAttribute("err", -3);
            return "Employees";
        }
        restController.addDepartment(fName, lName, pName, position, salary);
        model.addAttribute("err", 0);
        return "Employees";
    }
}
