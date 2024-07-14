package com.timetracker.tracker.mappers;

import com.timetracker.tracker.dto.req.CreateRecordDTO;
import com.timetracker.tracker.dto.req.UpdateRecordDTO;
import com.timetracker.tracker.dto.resp.RecordDTO;
import com.timetracker.tracker.dto.resp.RecordsForPageDTO;
import com.timetracker.tracker.entities.Project;
import com.timetracker.tracker.entities.Record;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.exceptions.InvalidRecordPeriod;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.sql.Date;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.timetracker.tracker.utils.Constants.*;

/**
 * Mapper interface for mapping between entities and DTOs for records.
 */
@Mapper
public interface RecordMapper {
    /**
     * Instance of the RecordMapper interface.
     */
    RecordMapper INSTANCE = Mappers.getMapper(RecordMapper.class);

    /**
     * Maps CreateRecordDTO to Record entity.
     *
     * @param user    The user entity.
     * @param project The project entity.
     * @param dto     The CreateRecordDTO object.
     * @return The mapped Record entity.
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "user", target = "user"),
            @Mapping(source = "project", target = "project"),
            @Mapping(expression = "java(getEndDate(dto.getStartDate(), dto.getDuration()))", target = "endDate"),
            @Mapping(expression = "java(dto.getDescription())", target = "description")
    })
    Record toEntity(User user, Project project, CreateRecordDTO dto);

    /**
     * Maps Record entity to RecordDTO.
     *
     * @param entity The Record entity.
     * @return The mapped RecordDTO.
     */
    @Mappings({
            @Mapping(expression = "java(getUserFullName(entity.getUser()))", target = "userFullName"),
            @Mapping(expression = "java(getProjectName(entity.getProject()))", target = "projectName"),
            @Mapping(expression = "java(getDurationAsString(entity.getDuration()))", target = "duration")
    })
    RecordDTO toDTO(Record entity);

    /**
     * Merges update request with Record entity.
     *
     * @param record The existing Record entity.
     * @param req    The UpdateRecordDTO request.
     * @return The updated Record entity.
     */
    default Record mergeReqAndEntity(Record record, UpdateRecordDTO req) {
        if (Objects.nonNull(req.getDescription())) {
            record.setDescription(req.getDescription());
        }
        if (Objects.nonNull(req.getStartDate())) {
            record.setStartDate(req.getStartDate());
        }
        if (Objects.nonNull(req.getDuration())) {
            record.setDuration(req.getDuration());
        }
        if (Objects.nonNull(req.getStartDate()) || Objects.nonNull(req.getDuration())) {
            Duration duration = Objects.requireNonNullElse(req.getDuration(), record.getDuration());
            Date endDate = getEndDate(req.getStartDate(), duration);
            record.setEndDate(endDate);
        }
        return record;
    }

    /**
     * Converts a list of Record entities to RecordsForPageDTO.
     *
     * @param records    The list of Record entities.
     * @param totalItems The total number of items.
     * @return The RecordsForPageDTO object.
     */
    default RecordsForPageDTO toRecordList(List<Record> records, Long totalItems) {
        List<RecordDTO> result = records.stream()
                .map(INSTANCE::toDTO)
                .toList();
        return new RecordsForPageDTO(result, totalItems);
    }

    /**
     * Gets the full name of the user.
     *
     * @param user The User entity.
     * @return The full name of the user.
     */
    default String getUserFullName(User user) {
        return Optional.ofNullable(user)
                .map(u -> String.format(USER_FULL_NAME_PATTERN, u.getName(), u.getSurname()))
                .orElse(StringUtils.EMPTY);
    }

    /**
     * Gets the project name.
     *
     * @param project The Project entity.
     * @return The name of the project.
     */
    default String getProjectName(Project project) {
        return Optional.ofNullable(project)
                .map(Project::getName)
                .orElse(StringUtils.EMPTY);
    }

    /**
     * Calculates the end date based on start date and duration.
     *
     * @param startDate The start date.
     * @param duration  The duration of the record.
     * @return The calculated end date.
     * @throws InvalidRecordPeriod if start date or duration is null.
     */
    default Date getEndDate(Date startDate, Duration duration) {
        if (Objects.nonNull(startDate) && Objects.nonNull(duration)) {
            long millisecondsToAdd = duration.toMillis();
            long updatedEndDate = startDate.getTime() + millisecondsToAdd;
            return new Date(updatedEndDate);
        }
        throw new InvalidRecordPeriod();
    }

    /**
     * Converts duration to a formatted string.
     *
     * @param duration The duration.
     * @return The formatted duration string.
     */
    default String getDurationAsString(Duration duration) {
        long days = duration.toDays();
        long hours = duration.minusDays(days).toHours();
        long minutes = duration.minusDays(days).minusHours(hours).toMinutes();

        long weeks = days / DAYS_IN_A_WEEK;
        days = days % DAYS_IN_A_WEEK;

        return String.format(DURATION_FORMAT_PATTERN, weeks, days, hours, minutes);
    }
}
