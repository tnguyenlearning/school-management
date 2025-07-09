package com.school.billing.dtos;

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

	private String paymentId;
	private BigDecimal refundAmount;
	private String reason;

}
