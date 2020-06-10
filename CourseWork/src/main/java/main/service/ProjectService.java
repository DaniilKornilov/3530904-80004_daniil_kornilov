package main.service;

import main.entity.Project;

import java.util.List;

public interface ProjectService {
  List<Project> listProjects();
  Project getProjects(Integer id);
  Project addProjects(Project project);

  void deleteAllProjects();
  void deleteProject(int id);
}
