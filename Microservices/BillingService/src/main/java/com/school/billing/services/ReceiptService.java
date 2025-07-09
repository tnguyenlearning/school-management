package com.school.billing.services;

import org.springframework.stereotype.Service;

import com.school.billing.entities.Receipt;
import com.school.billing.entities.StudentAccount;
import com.school.billing.repositories.ReceiptRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceiptService {

	private final ReceiptRepository receiptRepository;
	private final StudentAccountService studentAccountService;

	@Transactional
	public Receipt create(Long studentAccountId, Receipt request) {
		StudentAccount stAccount = studentAccountService.findById(studentAccountId);
		stAccount.setBalanceAmount(stAccount.getBalanceAmount().add(request.getAmount()));
		studentAccountService.save(stAccount);
		
		request.setStudentAccount(stAccount);
		return receiptRepository.save(request);
	}

}
