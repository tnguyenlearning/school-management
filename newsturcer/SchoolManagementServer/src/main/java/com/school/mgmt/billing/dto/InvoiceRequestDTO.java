package com.school.mgmt.billing.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequestDTO {

	private String studentId;
	private String courseId;
	private BigDecimal amount;

}
