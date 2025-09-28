package com.school.billing.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.school.billing.constants.RefundType;

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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "session_refund")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionRefund {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long leaveRequestId; // Từ education-service

	@NotNull
	private String studentCode;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RefundType refundType; // MONEY or CREDIT_SESSION

	@Column(precision = 12, scale = 2)
	private BigDecimal refundAmount; // Nullable nếu CREDIT_SESSION

	private String reason;

	private LocalDate refundedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "billing_cycle_id")
	private BillingCycle billingCycle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id")
	private Invoice invoice; // Chỉ dùng khi hoàn tiền

	@Builder.Default
	private boolean consumed = false; // true khi đã được áp dụng vào billing cycle
}
