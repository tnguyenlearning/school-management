package com.school.billing.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectionRequestDTO {
	
	private String fromStudentCode;
	private String toStudentCode;
	private LocalDate effDate;
	
}
