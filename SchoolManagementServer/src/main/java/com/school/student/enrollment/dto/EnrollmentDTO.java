package com.school.student.enrollment.dto;

import java.time.LocalDate;

import com.school.constant.EnrollmentStatus;
import com.school.exception.BadRequestException;
import com.school.tuition.entity.CourseFrequencyOption;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO {

	@NotNull(message = "Enrollment date cannot be empty")
	private LocalDate enrollmentDate;

	@NotNull(message = "Start date cannot be empty")
	private LocalDate startDate;

	private LocalDate endDate; // Can be null for indefinite courses

	@NotNull(message = "Status cannot be empty")
	@Enumerated(EnumType.STRING)
	private EnrollmentStatus status;

	private CourseFrequencyOption frequencyOption;

	private Integer debtDuration;

	private Integer validFlag;

	private LocalDate nextGenerateBillingDate;

	public void validate() {
		if (!EnrollmentStatus.ENROLLMENT_STATUS.contains(this.status)) {
			throw new BadRequestException("Invalid enrollment status " + this.status);
		}
		if (startDate == null || endDate == null) {
			return;
		}
		if (endDate != null && endDate.isBefore(startDate)) {
			throw new BadRequestException("Start Date must be before end date");
		}
		
	}
	
}
