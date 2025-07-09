package com.school.student.enrollment.evenhandler;

import java.time.LocalDate;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.school.constant.BillingAction;
import com.school.exception.NotFoundException;
import com.school.student.education.service.EnrollmentService;
import com.school.student.enrollment.entity.Enrollment;
import com.school.student.enrollment.repository.EnrollmentRepository;
import com.school.tuition.service.BillingCycleService;
import com.school.validator.StatusValidator;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RepositoryEventHandler(Enrollment.class)
@RequiredArgsConstructor
public class EnrollmentEventHandler {

	private final BillingCycleService billingCycleService;
	private final EnrollmentService enrollmentService;

	@HandleBeforeCreate
	public void handleBeforeCreate(Enrollment updatedEnrollment) {
		LocalDate nextGenerate = billingCycleService.calcNextGenerateBillingDate(updatedEnrollment,
				BillingAction.CREATE);
		updatedEnrollment.setValidflag(1);
		updatedEnrollment.setNextGenerateBillingDate(nextGenerate);
		updatedEnrollment.validate();
	}

	@HandleBeforeSave
	@Transactional
	public void handleBeforeSave(Enrollment updatedEnrollment) {
		enrollmentService.beforeChangeValidate(updatedEnrollment);

		updatedEnrollment.validate();
	}

}
