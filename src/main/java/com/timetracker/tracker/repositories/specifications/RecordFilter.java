package com.timetracker.tracker.repositories.specifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * This class represents a filter used for searching records in the database.
 * It contains criteria such as userId, projectId, startDate, and endDate.
 * These criteria can be used to create a JPA specification for querying records from the database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordFilter {

    /**
     * The user ID to filter by. May be {@literal null}.
     */
    private Long userId;

    /**
     * The project ID to filter by. May be {@literal null}.
     */
    private Long projectId;

    /**
     * The record start date to filter by. May be {@literal null}.
     */
    private Date startDate;

    /**
     * The record end date to filter by. May be {@literal null}.
     */
    private Date endDate;

}
