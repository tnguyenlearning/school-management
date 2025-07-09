package com.school.billing.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "invoice_refund")
public class InvoiceRefund {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id", nullable = false)
	private Invoice invoice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "refund_id", nullable = false)
	private Refund refund;
	
	@Column(precision = 17, scale = 2, nullable = false)
	private BigDecimal refundAmount;
	
	@Column(nullable = false)
	private Integer refundLearningDays;
	
	@Column(nullable = false)
	private LocalDate refundDate;
	
}
