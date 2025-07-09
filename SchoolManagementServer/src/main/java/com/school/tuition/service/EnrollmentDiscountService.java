package com.school.tuition.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.school.tuition.entity.Discount;
import com.school.tuition.entity.EnrollmentDiscount;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentDiscountService {

	public BigDecimal calculateTotalDiscountAmount(BigDecimal basicFeeAmount, LocalDate effDate,
			List<EnrollmentDiscount> enrollmentDiscounts) {
		BigDecimal totalDiscountAmount = BigDecimal.ZERO;

		for (EnrollmentDiscount eDiscount : enrollmentDiscounts) {
			// Check if the discount is valid and falls within the effective date range
			if (eDiscount.getValidflag() == 1 && !eDiscount.getValidFromDate().isAfter(effDate)
					&& !eDiscount.getValidToDate().isBefore(effDate)) {
				Discount discount = eDiscount.getDiscount();
				BigDecimal discountValue = discount.getValue();
				switch (discount.getType()) {
				case PERCENTAGE:
					// Calculate percentage-based discount
					BigDecimal percentageDiscount = basicFeeAmount.multiply(discountValue)
							.divide(BigDecimal.valueOf(100));
					totalDiscountAmount = totalDiscountAmount.add(percentageDiscount);
					break;

				case FIXED_AMOUNT:
					totalDiscountAmount = totalDiscountAmount.add(discountValue);
					break;

				default:
					throw new IllegalArgumentException("Unknown discount type: " + discount.getType());
				}
			}
		}

		return totalDiscountAmount;
	}

}
