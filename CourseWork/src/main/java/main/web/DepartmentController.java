package main.web;

import main.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DepartmentController {
    @Autowired
    RestController restController;

    @RequestMapping("/departments/main/{err}")
    public String showDep(@PathVariable String err, Model model) {
        model.addAttribute("error", err);
        return "Departments";
    }

    @RequestMapping("/departments/all")
    public String allDepartments(Model model) {
        List<Department> list = restController.getAllDepartments().getBody();
        model.addAttribute("list", list);
        return "Departments";
    }

    @RequestMapping("/departments/{id}")
    public String departmentById(Model model, @PathVariable String id) {
        try {
            Integer.parseInt(id);
        } catch (Exception exception) {
            model.addAttribute("err", -2);
            return "Departments";
        }
        Department dep = restController.getDepartment(Integer.parseInt(id)).getBody();
        if (dep == null) {
            model.addAttribute("err", -1);
            return "Departments";
        }
        model.addAttribute("department", dep);
        return "DepartmentID";
    }

    @RequestMapping("/departments/delete/{id}")
    public String deleteDepById(@PathVariable String id, Model model) {
        try {
            Integer.parseInt(id);
        } catch (Exception exception) {
            model.addAttribute("err", -2);
            return "Departments";
        }
        int err = restController.deleteDepartment(Integer.parseInt(id));
        model.addAttribute("err", err);
        return "Departments";
    }

    @RequestMapping("/departments/delete/")
    public String deleteDepById(Model model) {
        restController.deleteAllDepartments();
        model.addAttribute("err", 0);
        return "Departments";
    }

    @RequestMapping("/departments/update/{id}/{name}")
    public String updateDepById(@PathVariable String id, @PathVariable String name, Model model) {
        try {
            Integer.parseInt(id);
        } catch (Exception exception) {
            model.addAttribute("err", -2);
            return "Departments";
        }
        int err = restController.updateDepartment(Integer.parseInt(id), name);
        model.addAttribute("err", err);
        return "Departments";
    }

    @RequestMapping("/departments/add")
    public String addDep(
            @RequestParam(name = "name", required = false, defaultValue = "") String name, Model model
    ) {
        restController.addDepartment(name);
        model.addAttribute("err", 0);
        return "Departments";
    }
}
