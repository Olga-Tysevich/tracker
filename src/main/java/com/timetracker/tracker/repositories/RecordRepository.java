package com.timetracker.tracker.repositories;

import com.timetracker.tracker.entities.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long>, JpaSpecificationExecutor<Record> {
    Record getByStartDateAndProjectIdAndUserId(Date startDate, Long projectId, Long userId);

    Page<Record> getAllByUserId(Long userId, PageRequest pageRequest);

}
