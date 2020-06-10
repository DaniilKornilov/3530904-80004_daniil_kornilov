package main.web;

import main.entity.Department;
import main.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    RestController restController;

    @RequestMapping("/projects/main/{err}")
    public String showProj(@PathVariable String err, Model model) {
        model.addAttribute("error", err);
        return "Projects";
    }

    @RequestMapping("/projects/all")
    public String allProjects(Model model) {
        List<Project> list = restController.getAllProjects().getBody();
        model.addAttribute("list", list);
        return "Projects";
    }

    @RequestMapping("/projects/{id}")
    public String projectsById(Model model, @PathVariable String id) {
        try {
            Integer.parseInt(id);
        } catch (Exception exception) {
            model.addAttribute("err", -2);
            return "Projects";
        }
        Project proj = restController.getProject(Integer.parseInt(id)).getBody();
        if (proj == null) {
            model.addAttribute("err", -1);
            return "Projects";
        }
        model.addAttribute("project", proj);
        return "ProjectID";
    }

    @RequestMapping("/projects/delete/{id}")
    public String deleteProjById(@PathVariable String id, Model model) {
        try {
            Integer.parseInt(id);
        } catch (Exception exception) {
            model.addAttribute("err", -2);
            return "Projects";
        }
        int err = restController.deleteProject(Integer.parseInt(id));
        model.addAttribute("err", err);
        return "Projects";
    }

    @RequestMapping("/projects/delete/")
    public String deleteAllProjects(Model model) {
        restController.deleteAllProjects();
        model.addAttribute("err", 0);
        return "Projects";
    }

    public int dateChecker(String date) {
        try {
            int year, month, day;
            String[] tokens = date.split("/");
            year = Integer.parseInt(tokens[0]);
            month = Integer.parseInt(tokens[1]);
            day = Integer.parseInt(tokens[2]);
            if (month < 0 || month > 12 || day < 1 || day > 31 || year < 1900 || year > 9999) {
                return -1;
            }
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public String dateCorrector(String date) {
        int year, month, day;
        year = Integer.parseInt(date.substring(0, 4));
        month = Integer.parseInt(date.substring(5, 7));
        day = Integer.parseInt(date.substring(8));
        month -= 1;
        if (month < 10) {
            return year + "/0" + month + "/" + day;
        }
        return year + "/" + month + "/" + day;
    }

    @RequestMapping("/projects/update/{id}")
    public String updateProjectById(
            @PathVariable String id,
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "cost", required = false, defaultValue = "") String cost,
            @RequestParam(name = "depId", required = false, defaultValue = "") String depId,
            @RequestParam(name = "dateBeg", required = false, defaultValue = "") String dateBeg,
            @RequestParam(name = "dateEnd", required = false, defaultValue = "") String dateEnd,
            @RequestParam(name = "dateEndReal", required = false, defaultValue = "") String dateEndReal,
            Model model
    ) {
        try {
            Integer.parseInt(id);
        } catch (Exception exception) {
            model.addAttribute("err", -2);
            return "Projects";
        }
        if (!depId.equals("")) {
            try {
                Department dep = restController.getDepartment(Integer.parseInt(depId)).getBody();
                if (dep == null) {
                    model.addAttribute("err", -5);
                    return "Projects";
                }
            } catch (Exception e) {
                model.addAttribute("err", -2);
                return "Projects";
            }
        }
        Project oldProject = restController.getProject(Integer.parseInt(id)).getBody();
        if (oldProject == null) {
            model.addAttribute("err", -1);
            return "Projects";
        }
        if (!dateBeg.equals("") || !dateEnd.equals("") || !dateEndReal.equals("")) {
            if (!(dateChecker(dateBeg) == 0 && dateChecker(dateEnd) == 0 && dateChecker(dateEndReal) == 0)) {
                model.addAttribute("err", -4);
                return "Projects";
            }
        }
        if (name.equals("")) {
            name = oldProject.getName();
        }
        if (cost.equals("")) {
            cost = oldProject.getCost().toString();
        }
        if (depId.equals("")) {
            depId = oldProject.getDepId().toString();
        }
        if (dateBeg.equals("")) {
            dateBeg = oldProject.getDateBeg();
        } else {
            dateBeg = dateCorrector(dateBeg);
        }
        if (dateEnd.equals("")) {
            dateEnd = oldProject.getDateEnd();
        } else {
            dateEnd = dateCorrector(dateEnd);
        }
        if (dateEndReal.equals("")) {
            dateEndReal = oldProject.getDateEndReal();
        } else {
            dateEndReal = dateCorrector(dateEndReal);
        }

        try {
            Double.parseDouble(cost);
        } catch (Exception exception) {
            model.addAttribute("err", -3);
            return "Projects";
        }
        int err = restController.updateProjects(Integer.parseInt(id), name, cost, depId, dateBeg, dateEnd, dateEndReal);
        model.addAttribute("err", err);
        return "Projects";
    }

    @RequestMapping("/projects/add")
    public String addProject(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "cost", required = false, defaultValue = "") String cost,
            @RequestParam(name = "depId", required = false, defaultValue = "") String depId,
            @RequestParam(name = "dateBeg", required = false, defaultValue = "") String dateBeg,
            @RequestParam(name = "dateEnd", required = false, defaultValue = "") String dateEnd,
            @RequestParam(name = "dateEndReal", required = false, defaultValue = "") String dateEndReal,
            Model model
    ) {
        try {
            Double.parseDouble(cost);
        } catch (Exception exception) {
            model.addAttribute("err", -3);
            return "Projects";
        }
        if (!(dateChecker(dateBeg) == 0 && dateChecker(dateEnd) == 0 && dateChecker(dateEndReal) == 0)) {
            model.addAttribute("err", -4);
            return "Projects";
        }
        dateBeg = dateCorrector(dateBeg);
        dateEnd = dateCorrector(dateEnd);
        dateEndReal = dateCorrector(dateEndReal);
        try {
            Department dep = restController.getDepartment(Integer.parseInt(depId)).getBody();
            if (dep == null) {
                model.addAttribute("err", -5);
                return "Projects";
            }
        } catch (Exception e) {
            model.addAttribute("err", -2);
            return "Projects";
        }
        restController.addProject(name, cost, depId, dateBeg, dateEnd, dateEndReal);
        model.addAttribute("err", 0);
        return "Projects";
    }
}
