package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.dto.req.CreateProjectDTO;
import com.timetracker.tracker.dto.req.GetProjectsForPageDTO;
import com.timetracker.tracker.dto.req.UpdateProjectDTO;
import com.timetracker.tracker.dto.resp.ProjectsForPageDTO;
import com.timetracker.tracker.entities.Project;
import com.timetracker.tracker.exceptions.NotFoundException;
import com.timetracker.tracker.exceptions.ObjectAlreadyExist;
import com.timetracker.tracker.mappers.ProjectMapper;
import com.timetracker.tracker.repositories.ProjectRepository;
import com.timetracker.tracker.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public void createProject(CreateProjectDTO req) {
        Optional.ofNullable(req)
                .map(ProjectMapper.INSTANCE::toEntity)
                .map(this::checkProjectName)
                .ifPresent(projectRepository::save);
    }

    @Override
    public void deleteProject(Long id) {

    }

    @Override
    public void updateProject(UpdateProjectDTO req) {

    }

    @Override
    public ProjectsForPageDTO getProjectsForPage(GetProjectsForPageDTO req) {
        Page<Project> result = Optional.ofNullable(req)
                .map(r -> PageRequest.of(r.getPageNum(), r.getCountPerPage()))
                .map(projectRepository::findAll)
                .orElseThrow(NotFoundException::new);
        return ProjectMapper.INSTANCE.toProjectList(result.getContent(), result.getTotalElements());
    }

    private Project checkProjectName(Project project) {
        if (projectRepository.existsByName(project.getName())) {
            throw new ObjectAlreadyExist("Project already exist");
        }
        return project;
    }
}