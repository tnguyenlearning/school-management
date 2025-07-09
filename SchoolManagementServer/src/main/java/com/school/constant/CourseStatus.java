package com.school.constant;

import java.util.Set;

public enum CourseStatus {
	PLANNED,
	ONGOING,
    COMPLETED,
    CANCELED,
    ON_HOLD;
    
    public static final Set<CourseStatus> COURSE_STATUS = Set.of(
	        PLANNED,
	        ONGOING,
	        COMPLETED,
	        CANCELED,
	        ON_HOLD
	 );
}

