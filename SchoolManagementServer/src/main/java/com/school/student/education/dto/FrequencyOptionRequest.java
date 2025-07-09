package com.school.student.education.dto;

import java.math.BigDecimal;

import com.school.constant.Frequency;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FrequencyOptionRequest {

	@NotNull(message = "Frequency cannot be empty")
	private Frequency frequency;

	private Integer totalLearningDays;

	@NotNull(message = "Fee amount cannot be empty")
	private BigDecimal feeAmount;

}
