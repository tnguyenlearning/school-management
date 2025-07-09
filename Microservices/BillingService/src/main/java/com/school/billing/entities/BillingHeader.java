package com.school.billing.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "billing_header")
public class BillingHeader {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "EnrollmentID cannot be empty")
	@Column(nullable = false)
	private Long enrollmentId;

	@NotNull(message = "StudentCode cannot be empty")
	@Column(nullable = false)
	private String studentCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "billing_frequency_option_id", nullable = false)
	private BillingFrequencyOption billingFrequencyOption;
	
	@Column(nullable = false)
	private LocalDate nextBillingDate;

}
