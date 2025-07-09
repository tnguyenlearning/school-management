package com.school.tuition.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundRequestDTO {

	private Long paymentId;
	private BigDecimal refundAmount;
	private String reason;

}
