package com.school.student.education.eventhandler;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.school.constant.GlobalConstants;
import com.school.student.education.entity.Course;

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
		if (course.getFrequencyOptions() != null) {
			course.getFrequencyOptions().forEach(option -> option.setCourse(course));
		}
	}

}
