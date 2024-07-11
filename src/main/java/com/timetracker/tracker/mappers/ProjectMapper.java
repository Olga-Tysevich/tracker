package com.timetracker.tracker.mappers;

import com.timetracker.tracker.dto.req.CreateProjectDTO;
import com.timetracker.tracker.dto.resp.ProjectDTO;
import com.timetracker.tracker.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    Project toEntity(CreateProjectDTO dto);

    ProjectDTO toDTO(Project entity);

}
