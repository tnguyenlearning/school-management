package com.school.education.enrollment.eventhandler;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.school.education.enrollment.entities.Enrollment;
import com.school.education.enrollment.service.EnrollmentService;

import lombok.RequiredArgsConstructor;

@Component
@RepositoryEventHandler(Enrollment.class)
@RequiredArgsConstructor
public class EnrollmentEventHandler {

	private final EnrollmentService enrollmentService;
//
//	@HandleBeforeCreate
//	public void handleBeforeCreate(Enrollment updatedEnrollment) {
//		// call API to create billing enrollment
//
//	}

//	@HandleBeforeSave
//	@Transactional
//	public void handleBeforeSave(Enrollment updatedEnrollment) {
//		enrollmentService.beforeChangeValidate(updatedEnrollment);
//
//	}

}
