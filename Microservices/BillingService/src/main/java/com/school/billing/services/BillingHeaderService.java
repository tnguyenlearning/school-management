package com.school.billing.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.school.billing.entities.BillingHeader;
import com.school.billing.repositories.BillingHeaderRepository;
import com.school.utilslibrary.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingHeaderService {

	private final BillingHeaderRepository billingHeaderRepository;

	public List<Long> getFilteredEnrollmentIdsForBilling(String fromStudentCode, String toStudentCode,
			LocalDate effDate) {

		return billingHeaderRepository.findEnrollmentIdsByStudentCodeRange(fromStudentCode, toStudentCode, effDate);

	}

	public BillingHeader findByEnrollmentId(Long enrollmentId) {
		BillingHeader header = billingHeaderRepository.findByEnrollmentId(enrollmentId);
		if (header == null) {
			throw new NotFoundException("BillingHeader with ID " + enrollmentId + " not found.");
		}

		return header;
	}
	
	public BillingHeader save(BillingHeader billingHeader) {
		return billingHeaderRepository.save(billingHeader);
	}
}
