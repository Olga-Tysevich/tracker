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
import java.util.Objects;

/**
 * Mapper interface for mapping Project entities and DTOs.
 */
@Mapper
public interface ProjectMapper {
    /**
     * Singleton instance of the ProjectMapper interface.
     */
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    /**
     * Maps a CreateProjectDTO to a Project entity.
     *
     * @param dto the CreateProjectDTO to map.
     * @return the mapped Project entity.
     */
    @Mapping(target = "id", ignore = true)
    Project toEntity(CreateProjectDTO dto);

    /**
     * Merges attributes of an UpdateProjectDTO into a Project entity.
     *
     * @param project the existing Project entity.
     * @param req     the UpdateProjectDTO with updated attributes.
     * @return the updated Project entity.
     */
    default Project mergeReqAndEntity(Project project, UpdateProjectDTO req) {
        project.setName(Objects.requireNonNullElse(req.getName(), project.getName()));
        project.setDescription(Objects.requireNonNullElse(req.getDescription(), project.getName()));
        return project;
    }

    /**
     * Maps a Project entity to a ProjectDTO.
     *
     * @param entity the Project entity to map.
     * @return the mapped ProjectDTO.
     */
    ProjectDTO toDTO(Project entity);

    /**
     * Maps a list of Project entities to ProjectsForPageDTO including total items count.
     *
     * @param projects   the list of Project entities.
     * @param totalItems the total count of items.
     * @return the mapped ProjectsForPageDTO.
     */
    default ProjectsForPageDTO toProjectList(List<Project> projects, Long totalItems) {
        List<ProjectDTO> result = projects.stream()
                .map(INSTANCE::toDTO)
                .toList();
        return new ProjectsForPageDTO(result, totalItems);
    }

}
