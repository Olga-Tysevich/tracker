package com.timetracker.tracker.repositories.specifications;

import com.timetracker.tracker.entities.Project_;
import com.timetracker.tracker.entities.Record;
import com.timetracker.tracker.entities.Record_;
import com.timetracker.tracker.entities.User_;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.criteria.Predicate;

/**
 * The class representing a specification for searching records based on a provided filter.
 */
public class RecordSpecification {

    /**
     * Creates a specification based on the provided filter.
     * @param filter The filter used to search for records.
     * @return A Specification object representing the search criteria.
     */
    public static Specification<Record> search(RecordFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(filter)) {
                if (Objects.nonNull(filter.getUserId())) {
                    predicates.add(cb.equal(root.get(Record_.USER).get(User_.ID), filter.getUserId()));
                }
                if (Objects.nonNull(filter.getProjectId())) {
                    predicates.add(cb.equal(root.get(Record_.PROJECT).get(Project_.ID), filter.getProjectId()));
                }
                if (Objects.nonNull(filter.getStartDate()) && Objects.nonNull(filter.getEndDate())) {
                    predicates.add(cb.between(root.get(Record_.START_DATE), filter.getStartDate(), filter.getEndDate()));
                    predicates.add(cb.between(root.get(Record_.END_DATE), filter.getStartDate(), filter.getEndDate()));
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
