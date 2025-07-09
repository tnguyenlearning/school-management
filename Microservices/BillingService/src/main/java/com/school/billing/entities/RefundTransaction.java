package com.school.billing.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.school.billing.constants.PayoutMethod;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refund_transaction")
public class RefundTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "refund_id", nullable = false)
	private Refund refund;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "billing_cycle_id", nullable = false)
	private BillingCycle appliedCycle;

	@Column(nullable = false, precision = 17, scale = 2)
	private BigDecimal refundAmount;

	@Column(nullable = false)
	private Integer learningDays;

	@Enumerated(EnumType.STRING)
	private PayoutMethod payoutMethod;

	@Column(nullable = false)
	private LocalDate effdate;

}
