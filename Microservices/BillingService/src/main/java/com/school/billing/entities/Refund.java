package com.school.billing.entities;

import java.math.BigDecimal;
import java.util.List;

import com.school.billing.constants.RefundReason;
import com.school.billing.constants.RefundStatus;
import com.school.billing.constants.RefundType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

	@NotNull(message = "StudentId cannot be empty")
	@Column(nullable = false)
	private String studentId;

	@OneToMany(mappedBy = "refund", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<InvoiceRefund> invoiceRefunds;
	
	@OneToMany(mappedBy = "refund", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<RefundTransaction> refundTransactions;

	@Column(nullable = false, precision = 17, scale = 2)
	private BigDecimal totalAmount;

	@Column(nullable = false)
	private Integer totalDays;

	@Column(nullable = false, precision = 17, scale = 2)
	private BigDecimal remainingAmount;

	@Column(nullable = false)
	private Integer remainingDays;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RefundReason reason;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RefundType type;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RefundStatus status;

}
