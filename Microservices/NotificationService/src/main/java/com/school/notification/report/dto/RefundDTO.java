package com.school.notification.report.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO {
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate refundStatementDate;
	private BigDecimal value;
	private String name;
}
