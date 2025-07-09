package com.school.tuition.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateBillingRequestDTO {
	
	private Long fromStudentId;
	private Long toStudentId;
	private LocalDate effDate;
	
}
