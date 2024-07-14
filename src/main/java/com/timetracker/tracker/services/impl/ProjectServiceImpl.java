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

import java.util.Objects;
import java.util.Optional;

import static com.timetracker.tracker.utils.Constants.REQ_CANNOT_BE_NULL;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public void createProject(CreateProjectDTO req) {
        if (Objects.isNull(req)) {
            throw new IllegalArgumentException(REQ_CANNOT_BE_NULL);
        }
        Project project = ProjectMapper.INSTANCE.toEntity(req);
        checkProjectName(project);
        projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        Optional.ofNullable(id).ifPresent(projectRepository::deleteById);
    }

    @Override
    public void updateProject(UpdateProjectDTO req) {
        if (Objects.isNull(req)) {
            throw new IllegalArgumentException(REQ_CANNOT_BE_NULL);
        }
        Project project = projectRepository.findById(req.getId())
                .orElseThrow(NotFoundException::new);
        Project forUpdate = ProjectMapper.INSTANCE.mergeReqAndEntity(project, req);
        projectRepository.save(forUpdate);
    }

    @Override
    public ProjectsForPageDTO getProjectsForPage(GetProjectsForPageDTO req) {
        Page<Project> result = Optional.ofNullable(req)
                .map(r -> PageRequest.of(r.getPageNum(), r.getCountPerPage()))
                .map(projectRepository::findAll)
                .orElseThrow(NotFoundException::new);
        return ProjectMapper.INSTANCE.toProjectList(result.getContent(), result.getTotalElements());
    }

    private void checkProjectName(Project project) {
        if (projectRepository.existsByName(project.getName())) {
            throw new ObjectAlreadyExist("Project already exist");
        }
    }
}
