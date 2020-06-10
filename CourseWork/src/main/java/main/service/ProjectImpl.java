package main.service;

import main.entity.Project;
import main.exeptions.EntityNotFoundException;
import main.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectImpl implements ProjectService {

    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public List<Project> listProjects() {
        return (List<Project>) projectRepo.findAll();
    }

    @Override
    public Project getProjects(Integer id) {
        Optional<Project> optionalDep = projectRepo.findById(id);
        if (optionalDep.isPresent()) {
            return optionalDep.get();
        } else {
            throw new EntityNotFoundException("Project not found");
        }
    }

    @Override
    public Project addProjects(Project project) {
        return projectRepo.save(project);
    }

    @Override
    public void deleteAllProjects() {
        projectRepo.deleteAll();
    }

    @Override
    public void deleteProject(int id) {
        projectRepo.deleteById(id);
    }
}
