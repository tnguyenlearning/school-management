package com.school.billing.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.school.billing.constants.BillingCycleStatus;
import com.school.billing.constants.Frequency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateBillingResponseDTO {

	private Long id;
	private String courseCode;
	private String studentCode;

	private Long enrollmentId;
	private Frequency frequency;
	private Integer totalLearningDays;

	private LocalDate generatedDate;
	private LocalDate cycleStartDate;
	private LocalDate cycleEndDate;
	private LocalDate nextCycleStartDate;

	private BigDecimal totalFee;
	private BigDecimal totalDiscount;
	private BigDecimal totalPaid;

	private Integer cycleNum;
	private BillingCycleStatus status;

}
