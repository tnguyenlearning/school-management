package com.school.billing.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.school.billing.entities.BillingHeader;
import com.school.billing.repositories.BillingHeaderRepository;
import com.school.utilslibrary.clients.billing.dtos.BulkNextBillingDateRequest;
import com.school.utilslibrary.exception.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingHeaderService {

	private final BillingHeaderRepository repository;

	public List<Long> getFilteredEnrollmentIdsForBilling(String fromStudentCode, String toStudentCode,
			LocalDate effDate) {

		return repository.findEnrollmentIdsByStudentCodeRange(fromStudentCode, toStudentCode, effDate);

	}

	public BillingHeader findByEnrollmentId(Long enrollmentId) {
		BillingHeader header = repository.findByEnrollmentId(enrollmentId);
		if (header == null) {
			throw new NotFoundException("BillingHeader with ID " + enrollmentId + " not found.");
		}

		return header;
	}

	public BillingHeader save(BillingHeader billingHeader) {
		return repository.save(billingHeader);
	}

	@Transactional
	public int bulkUpdateNextBillingDate(BulkNextBillingDateRequest request) {
		if (request.getEnrollmentIds() != null && !request.getEnrollmentIds().isEmpty()) {
			return repository.updateNextBillingDateIfEarlier(request.getEnrollmentIds(),
					request.getNextBillingDate());
		}
		return 0;
	}
	
}
