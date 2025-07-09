package com.school.student.enrollment.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.school.constant.EnrollmentStatus;
import com.school.exception.BadRequestException;
import com.school.student.education.entity.Course;
import com.school.tuition.entity.CourseFrequencyOption;
import com.school.tuition.entity.EnrollmentDiscount;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ENROLLMENT", uniqueConstraints = {
		@UniqueConstraint(columnNames = "enrollmentNum", name = "UK_enrollmentNum") })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Enrollment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<EnrollmentDiscount> enrollmentDiscounts;

	@NotNull(message = "Enrollment date cannot be empty")
	private LocalDate enrollmentDate;

	@NotNull(message = "Start date cannot be empty")
	private LocalDate startDate;

	private LocalDate endDate; // Can be null for indefinite courses

	@NotNull(message = "Status cannot be empty")
	@Enumerated(EnumType.STRING)
	private EnrollmentStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "frequency_option_id", nullable = false)
	private CourseFrequencyOption frequencyOption;

	private Integer debtDuration;

	private Integer validflag;

	private LocalDate nextGenerateBillingDate;

	private LocalDate billToDate;

	private LocalDate paidToDate;

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
		if (frequencyOption == null) {
			throw new BadRequestException("Frequency option must be selected for enrollment.");
		}
	}

}
