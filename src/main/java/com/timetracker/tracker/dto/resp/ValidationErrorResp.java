package com.timetracker.tracker.dto.resp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * This class represents a response object containing validation errors. *
 * It is used to encapsulate a list of ConstraintViolation objects.
 */
@Getter
@RequiredArgsConstructor
public class ValidationErrorResp {

    private final List<ConstraintViolation> violations;

}
