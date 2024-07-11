package com.timetracker.tracker.services;

import com.timetracker.tracker.dto.req.CreateProjectDTO;
import com.timetracker.tracker.dto.req.GetProjectsForPageDTO;
import com.timetracker.tracker.dto.req.UpdateProjectDTO;
import com.timetracker.tracker.dto.resp.ProjectsForPageDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {

    void createProject(CreateProjectDTO req);

    void deleteProject(Long id);

    void updateProject(UpdateProjectDTO req);

    ProjectsForPageDTO getProjectsForPage(GetProjectsForPageDTO req);

}
