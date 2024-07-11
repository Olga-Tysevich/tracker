package com.timetracker.tracker.dto.resp;

import com.timetracker.tracker.entities.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectsForPageDTO {

    private List<Project> projectsForPage;

    private Integer totalItems;

}
