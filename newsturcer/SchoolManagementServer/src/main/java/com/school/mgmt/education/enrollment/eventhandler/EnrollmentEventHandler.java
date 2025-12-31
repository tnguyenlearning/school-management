package com.school.mgmt.education.enrollment.eventhandler;

import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.school.mgmt.education.enrollment.entities.Enrollment;
import com.school.mgmt.education.enrollment.service.EnrollmentService;

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
