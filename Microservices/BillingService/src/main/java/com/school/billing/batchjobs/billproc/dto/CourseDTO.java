package com.school.billing.batchjobs.billproc.dto;

import java.time.DayOfWeek;
import java.util.Set;

public record CourseDTO(Long id, Set<DayOfWeek> daysOfWeek) {
}
