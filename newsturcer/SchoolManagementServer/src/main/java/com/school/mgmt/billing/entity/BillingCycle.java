package com.school.mgmt.billing.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.utilslibrary.constant.billing.BillingCycleStatus;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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

	@NotNull(message = "CourseCode cannot be empty")
	@Column(nullable = false)
	private String courseCode;

	@NotNull(message = "StudentCode cannot be empty")
	@Column(nullable = false)
	private String studentCode;

	@NotNull(message = "EnrollmentId cannot be empty")
	@Column(nullable = false)
	private Long enrollmentId;

	@ManyToOne
	@JoinColumn(name = "billing_frequency_option_id", nullable = false)
	private BillingFrequencyOption billingFrequencyOption;

	@OneToMany(mappedBy = "billingCycle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Invoice> invoices;

	@Column(nullable = false)
	private LocalDate generatedDate;

	@Column(nullable = false)
	private LocalDate cycleStartDate;

	@Column(nullable = false)
	private LocalDate cycleEndDate;

	@Column(nullable = false)
	private Integer totalLearningDays;

	@Column(nullable = false)
	@Default
	private Integer creditedSessions = 0;

	@Enumerated(EnumType.STRING)
	private BillingCycleStatus status;

	private Integer cycleNum;

}
