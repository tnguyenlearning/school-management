package com.school.tuition.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {

	private Long invoiceId;
	private Long studentId;
    private BigDecimal amount; // Used only for PARTIAL payments
	private LocalDate paymentDate;

}
