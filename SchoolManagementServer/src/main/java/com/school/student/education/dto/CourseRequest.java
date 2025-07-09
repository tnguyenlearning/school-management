package com.school.student.education.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import com.school.constant.CourseStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {

	@NotBlank(message = "Code cannot be empty")
	private String code;

	@NotBlank(message = "Name cannot be empty")
	private String name;

	private String description;

	@NotNull(message = "Status cannot be empty")
	private CourseStatus status;

	@NotNull(message = "Start date cannot be empty")
	private LocalDate startDate;

	private LocalDate endDate;

	@NotNull(message = "Start time cannot be empty")
	private LocalTime startTime;

	@NotNull(message = "End time cannot be empty")
	private LocalTime endTime;

	@NotNull(message = "Days of week cannot be empty")
	private Set<DayOfWeek> daysOfWeek;

	@NotNull(message = "Frequency options cannot be empty")
	private List<FrequencyOptionRequest> frequencyOptions;

}
