package com.school.tuition.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.school.constant.Frequency;
import com.school.constant.InvoiceStatus;
import com.school.constant.InvoiceType;
import com.school.constant.PaymentType;
import com.school.student.education.entity.Course;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "invoice")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "enrollment_id", nullable = false)
	private Enrollment enrollment;

	@ManyToOne
	@JoinColumn(name = "billing_cycle_id", nullable = false)
	private BillingCycle billingCycle;

	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
	private List<Payment> payments;

	@Column(precision = 17, scale = 2)
	private BigDecimal amountDue;

	@Column(precision = 17, scale = 2)
	private BigDecimal amountPaid;

	private LocalDate issueDate;

	private LocalDate publicDate;

	private LocalDate dueDate;

	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	@Enumerated(EnumType.STRING)
	private InvoiceStatus status;

	@Enumerated(EnumType.STRING)
	private InvoiceType type;

	private String description;

	@PrePersist
	@PreUpdate
	private void updateStatus() {
		if (amountPaid.compareTo(amountDue) >= 0) {
			status = InvoiceStatus.PAID;
		} else if (amountPaid.compareTo(BigDecimal.ZERO) > 0) {
			status = InvoiceStatus.PARTIALLY_PAID;
		} else {
			status = InvoiceStatus.UNPAID;
		}
	}

	public static InvoiceType getInvoiceTypeByFrequency(Frequency frequency) {
		switch (frequency) {
		case MONTHLY:
			return InvoiceType.MONTHLY_FEE;
		case QUARTERLY:
			return InvoiceType.QUARTERLY_FEE;
		case ANNUAL:
			return InvoiceType.ANNUAL_FEE;
		case PER_CLASS:
			return InvoiceType.PER_CLASS_FEE;
		case LEARNING_PERIOD:
			return InvoiceType.LEARNING_PERIOD_FEE;
		case WEEKLY:
			return InvoiceType.WEEKLY_FEE;
		case CUSTOM:
			return InvoiceType.CUSTOM_FEE;
		case SINGLE:
		default:
			// Default case for one-time payments, could be a REGISTRATION_FEE or similar
			return InvoiceType.REGISTRATION_FEE;
		}
	}

}
