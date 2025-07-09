package com.school.education.course.eventhandlers;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.school.education.course.entities.Course;
import com.school.utilslibrary.constants.GlobalConstants;

import lombok.RequiredArgsConstructor;

@Component
@RepositoryEventHandler(Course.class)
@RequiredArgsConstructor
public class CourseEventHandler {

	@HandleBeforeCreate
	public void handleBeforeCreate(Course course) {
		if (course.getEndDate() == null) {
			course.setEndDate(GlobalConstants.MAX_DATE);
		}
		course.validate();
		
		// call api to create billingenrollment
	}
	
}
