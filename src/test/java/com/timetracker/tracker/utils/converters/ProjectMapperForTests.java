package com.timetracker.tracker.utils.converters;

import com.timetracker.tracker.dto.req.CreateProjectDTO;
import com.timetracker.tracker.dto.req.UpdateProjectDTO;
import com.timetracker.tracker.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for mapping Project entities and DTOs.
 */
@Mapper
public interface ProjectMapperForTests {
    /**
     * Singleton instance of the ProjectMapper interface.
     */
    ProjectMapperForTests INSTANCE = Mappers.getMapper(ProjectMapperForTests.class);

    /**
     * Maps a Project entity to a CreateProjectDTO.
     *
     * @param entity the Project entity to map.
     * @return the mapped CreateProjectDTO.
     */
    CreateProjectDTO toCreateProjectDTO(Project entity);

    /**
     * Maps a Project entity to a toUpdateProjectDTO.
     *
     * @param entity the Project entity to map.
     * @return the mapped toUpdateProjectDTO.
     */
    UpdateProjectDTO toUpdateProjectDTO(Project entity);

}
