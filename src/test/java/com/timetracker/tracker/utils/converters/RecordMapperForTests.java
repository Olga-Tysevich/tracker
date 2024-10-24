package com.timetracker.tracker.utils.converters;

import com.timetracker.tracker.dto.req.CreateRecordDTO;
import com.timetracker.tracker.dto.req.UpdateRecordDTO;
import com.timetracker.tracker.entities.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for mapping between entities and DTOs for records.
 */
@Mapper
public interface RecordMapperForTests {
    /**
     * Instance of the RecordMapper interface.
     */
    RecordMapperForTests INSTANCE = Mappers.getMapper(RecordMapperForTests.class);

    /**
     * Maps Record entity to CreateRecordDTO.
     *
     * @param entity The Record entity.
     * @return The mapped CreateRecordDTO.
     */
    @Mappings({
            @Mapping(expression = "java(entity.getUser().getId())", target = "userId"),
            @Mapping(expression = "java(entity.getProject().getId())", target = "projectId"),
    })
    CreateRecordDTO toCreateRecordDTO(Record entity);

    /**
     * Maps Record entity to UpdateRecordDTO.
     *
     * @param entity The Record entity.
     * @return The mapped UpdateRecordDTO.
     */
    UpdateRecordDTO toUpdateRecordDTO(Record entity);

}
