package com.school.tuition.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.school.constant.BillingCycleStatus;
import com.school.constant.Frequency;

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
	private Long courseId;
	private String courseCode;
	private Long studentId;
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
	private BigDecimal totalRefund;
	private BigDecimal remainingBalance;

	private Integer cycleNum;
	private BillingCycleStatus status;

}
