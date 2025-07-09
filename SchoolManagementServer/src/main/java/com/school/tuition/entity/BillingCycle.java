package com.school.tuition.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.constant.BillingCycleStatus;
import com.school.constant.Frequency;
import com.school.student.enrollment.entity.Enrollment;
import com.school.student.enrollment.entity.Student;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "billing_cycle")
public class BillingCycle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "enrollment_id", nullable = false)
	@JsonIgnore
	private Enrollment enrollment;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	@JsonIgnore
	private Student student;

	@OneToMany(mappedBy = "billingCycle", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Invoice> invoices;

	@OneToMany(mappedBy = "billingCycle", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<BillingCycleDiscount> billingCycleDiscounts;

	@OneToMany(mappedBy = "billingCycle", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Refund> refunds;

	@Enumerated(EnumType.STRING)
	private Frequency frequency;

	private Integer totalLearningDays;

	private LocalDate generatedDate;

	private LocalDate cycleStartDate;

	private LocalDate cycleEndDate;

	private LocalDate nextCycleStartDate;

	@Column(precision = 17, scale = 2)
	private BigDecimal totalFee;

	@Column(precision = 17, scale = 2)
	private BigDecimal totalDiscount;

	@Column(precision = 17, scale = 2)
	@Builder.Default
	private BigDecimal totalPaid = BigDecimal.ZERO;

	@Column(precision = 17, scale = 2)
	@Builder.Default
	private BigDecimal totalRefund = BigDecimal.ZERO;

	@Column(precision = 17, scale = 2)
	private BigDecimal remainingBalance;

	@Enumerated(EnumType.STRING)
	private BillingCycleStatus status;

	private Integer cycleNum;

	public BigDecimal calcRemainingBalance() {
		return this.totalFee.subtract(this.totalDiscount).subtract(this.totalPaid).add(this.totalRefund);
	}
	
	public BigDecimal calcAmountDue() {
		return this.totalFee.subtract(this.totalDiscount);
	}

}
