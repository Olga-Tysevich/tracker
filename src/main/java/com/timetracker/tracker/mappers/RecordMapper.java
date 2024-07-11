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

import static com.timetracker.tracker.utils.Constants.USER_FULL_NAME_PATTERN;

@Mapper
public interface RecordMapper {
    RecordMapper INSTANCE = Mappers.getMapper(RecordMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(expression = "java(createUser(dto.getUserId()))", target = "user"),
            @Mapping(expression = "java(createProject(dto.getProjectId()))", target = "project"),
            @Mapping(expression = "java(getEndDate(dto.getStartDate(), dto.getDuration()))", target = "endDate")
    })
    Record reqToEntity(CreateRecordDTO dto);

    @Mappings({
            @Mapping(expression = "java(getUserFullName(entity.getUser()))", target = "userFullName"),
            @Mapping(expression = "java(getProjectName(entity.getProject()))", target = "projectName"),
            @Mapping(expression = "java(getDurationAsString(entity.getDuration()))", target = "duration")
    })
    RecordDTO toDTO(Record entity);


    default Record reqToEntity(Record record, UpdateRecordDTO req) {
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

    default RecordsForPageDTO toRecordList(List<Record> records, Long totalItems) {
        List<RecordDTO> result = records.stream()
                .map(INSTANCE::toDTO)
                .toList();
        return new RecordsForPageDTO(result, totalItems);
    }

    default String getUserFullName(User user) {
        return Optional.ofNullable(user)
                .map(u -> String.format(USER_FULL_NAME_PATTERN, u.getName(), u.getSurname()))
                .orElse(StringUtils.EMPTY);
    }

    default String getProjectName(Project project) {
        return Optional.ofNullable(project)
                .map(Project::getName)
                .orElse(StringUtils.EMPTY);
    }

    default User createUser(Long userId) {
        return User.builder()
                .id(userId)
                .build();
    }

    default Project createProject(Long projectId) {
        return Project.builder()
                .id(projectId)
                .build();
    }

    default Date getEndDate(Date startDate, Duration duration) {
        if (Objects.nonNull(startDate) && Objects.nonNull(duration)) {
            long millisecondsToAdd = duration.toMillis();
            long updatedEndDate = startDate.getTime() + millisecondsToAdd;
            return new Date(updatedEndDate);
        }
        throw new InvalidRecordPeriod();
    }

    default String getDurationAsString(Duration duration) {
        long days = duration.toDays();
        long hours = duration.minusDays(days).toHours();
        long minutes = duration.minusDays(days).minusHours(hours).toMinutes();

        long weeks = days / 7;
        days = days % 7;

        return String.format("%dw%dd%dh%dm", weeks, days, hours, minutes);
    }
}
