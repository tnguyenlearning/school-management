package com.school.tuition.entity;

import java.math.BigDecimal;

import com.school.constant.Frequency;
import com.school.exception.BadRequestException;
import com.school.student.education.entity.Course;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COURSE_FREQUENCY_OPTION")
public class CourseFrequencyOption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	@NotNull(message = "Course cannot be empty")
	private Course course;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Frequency cannot be empty")
	private Frequency frequency;

	private Integer totalLearningDays;

	@NotNull(message = "Fee amount cannot be empty")
	@Column(precision = 17, scale = 2)
	private BigDecimal feeAmount;

	public void validate() {
		if (frequency == null) {
			return;
		}
		if (this.frequency == Frequency.LEARNING_PERIOD) {
			if (this.totalLearningDays == null || this.totalLearningDays == 0) {
				throw new BadRequestException(
						"When frequency is LEARNING_PERIOD, totalLearningDays must be greater than ZERO.");
			}
		}
		if (feeAmount != null && feeAmount.compareTo(BigDecimal.ZERO) < 0) {
			throw new BadRequestException("Fee amount cannot be negative.");
		}
	}
}
