package com.school.tuition.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.school.constant.DiscountType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DISCOUNT")
public class Discount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DiscountType type;

	@Column(nullable = false, precision = 17, scale = 2)
	private BigDecimal value;

	@Column(nullable = false)
	private int remainingUses;

	private LocalDate validFromDate;

	private LocalDate validToDate;

	private String description;

	@Column(nullable = false)
	private Integer validflag;

	public void applyDiscount(int numberOfStudents) {
		if (numberOfStudents <= 0) {
			throw new IllegalArgumentException("Number of students must be greater than zero.");
		}

		if (numberOfStudents > remainingUses) {
			throw new IllegalStateException("Not enough remaining uses for the discount.");
		}

		remainingUses -= numberOfStudents;
	}

	public void applyDiscount() {
		if (remainingUses == 0) {
			throw new IllegalStateException("Not enough remaining uses for the discount.");
		}

		remainingUses -= 1;
	}

}
