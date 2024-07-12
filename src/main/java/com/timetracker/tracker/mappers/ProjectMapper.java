package com.timetracker.tracker.mappers;

import com.timetracker.tracker.dto.req.CreateProjectDTO;
import com.timetracker.tracker.dto.req.UpdateProjectDTO;
import com.timetracker.tracker.dto.resp.ProjectDTO;
import com.timetracker.tracker.dto.resp.ProjectsForPageDTO;
import com.timetracker.tracker.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(target = "id", ignore = true)
    Project toEntity(CreateProjectDTO dto);

    default Project mergeReqAndEntity(Project project, UpdateProjectDTO req) {
        project.setName(req.getName());
        project.setDescription(req.getDescription());
        return project;
    }

    ProjectDTO toDTO(Project entity);

    default ProjectsForPageDTO toProjectList(List<Project> projects, Long totalItems) {
        List<ProjectDTO> result = projects.stream()
                .map(INSTANCE::toDTO)
                .toList();
        return new ProjectsForPageDTO(result, totalItems);
    }

}
