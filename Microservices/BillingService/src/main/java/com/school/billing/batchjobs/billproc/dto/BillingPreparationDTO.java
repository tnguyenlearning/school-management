package com.school.billing.batchjobs.billproc.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

import com.school.billing.constants.EnrollmentStatus;
import com.school.billing.entities.BillingFrequencyOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingPreparationDTO {

	private Long id;

	private String enrollmentId;
	
	private String studentCode;
	
	private String courseCode;

	private LocalDate enrollmentStartDate;

	private LocalDate enrollmentEndDate;

	private EnrollmentStatus enrollmentStatus;

	private BillingFrequencyOption billingFrequencyOption;

	private LocalDate billingDate;

	private Set<DayOfWeek> daysOfWeek;

}
