package com.school.tuition.service;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.school.constant.BillingCycleStatus;
import com.school.tuition.entity.BillingCycle;

public class BillingCycleSpecification {

	public static Specification<BillingCycle> hasStatus(BillingCycleStatus status) {
		return (root, query, criteriaBuilder) -> status == null ? null
				: criteriaBuilder.equal(root.get("status"), status);
	}

	public static Specification<BillingCycle> hasStudentId(Long studentId) {
		return (root, query, criteriaBuilder) -> studentId == null ? null
				: criteriaBuilder.equal(root.get("student").get("id"), studentId);
	}

	public static Specification<BillingCycle> hasEnrollmentId(Long enrollmentId) {
		return (root, query, criteriaBuilder) -> enrollmentId == null ? null
				: criteriaBuilder.equal(root.get("enrollment").get("id"), enrollmentId);
	}

	public static Specification<BillingCycle> hasCycleNum(Integer cycleNum) {
		return (root, query, criteriaBuilder) -> cycleNum == null ? null
				: criteriaBuilder.equal(root.get("cycleNum"), cycleNum);
	}

	public static Specification<BillingCycle> hasCycleStartDateBetween(LocalDate startDate, LocalDate endDate) {
		return (root, query, criteriaBuilder) -> {
			if (startDate == null && endDate == null) {
				return null;
			}
			if (startDate != null && endDate != null) {
				return criteriaBuilder.between(root.get("cycleStartDate"), startDate, endDate);
			}
			if (startDate != null) {
				return criteriaBuilder.greaterThanOrEqualTo(root.get("cycleStartDate"), startDate);
			}
			return criteriaBuilder.lessThanOrEqualTo(root.get("cycleStartDate"), endDate);
		};
	}
	
}
