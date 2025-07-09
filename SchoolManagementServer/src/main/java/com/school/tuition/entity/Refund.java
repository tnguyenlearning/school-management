package com.school.tuition.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.school.constant.RefundStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "refund")
public class Refund {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "payment_id")
	private Payment payment;

	@Column(name = "refund_amount", precision = 10, scale = 2)
	private BigDecimal refundAmount;

	@Column(name = "refund_date")
	private LocalDate refundDate;

	@Column(name = "paid_out_money", precision = 10, scale = 2)
	private BigDecimal paidOutMoney;

	@Enumerated(EnumType.STRING)
	private RefundStatus status;
	
	@ManyToOne
	@JoinColumn(name = "billing_cycle_id")
	private BillingCycle billingCycle;
}
