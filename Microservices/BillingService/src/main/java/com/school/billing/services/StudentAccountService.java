package com.school.billing.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.billing.clients.EducationServiceClient;
import com.school.billing.constants.StudentAccountType;
import com.school.billing.controllers.StudentAccountDetailsDTO;
import com.school.billing.entities.StudentAccount;
import com.school.billing.repositories.PaymentRepository;
import com.school.billing.repositories.ReceiptRepository;
import com.school.billing.repositories.StudentAccountRepository;
import com.school.utilslibrary.exception.BadRequestException;
import com.school.utilslibrary.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentAccountService {

	private final StudentAccountRepository studentAccountRepository;
	private final EducationServiceClient educationServiceClient;
	private final ReceiptRepository receiptRepository;
	private final PaymentRepository paymentRepository;

	@Transactional
	public StudentAccount create(StudentAccount studentAccount) {
		if (!educationServiceClient.existsByStudentCode(studentAccount.getStudentCode())) {
			throw new BadRequestException("Student code " + studentAccount.getStudentCode() + " not found");
		}
		studentAccount.setBalanceAmount(BigDecimal.ZERO);
		return studentAccountRepository.save(studentAccount);
	}

	public Page<List<StudentAccount>> findStudentAccounts(String phone, String firstName, String studentCode,
			StudentAccountType type, Pageable pageable) {
		List<String> studentCodes = educationServiceClient.findStudentCodeContaining(phone, studentCode, firstName)
				.getData();
		return studentAccountRepository.findStudentAccounts(studentCodes, studentCode, type, pageable);
	}

	public StudentAccountDetailsDTO getStudentAccountDetails(Long studentAccountId) {
//		StudentAccount studentAccount = this.findById(studentAccountId);
//		List<Receipt> receipts = receiptRepository.findByStudentAccountId(studentAccountId);
//		List<Payment> payments = paymentRepository.findByStudentAccountId(studentAccountId);
//		return new StudentAccountDetailsDTO(studentAccount, receipts, payments);
		return null;
	}

	public StudentAccount findById(Long id) {
		return studentAccountRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("StudentAccount with ID " + id + " not found."));
	}
	
	public StudentAccount findByStudentCode(String studentCode) {
		return studentAccountRepository.findByStudentCode(studentCode)
			.orElseThrow(() -> new NotFoundException("StudentAccount not found for studentCode: " + studentCode));
	}

	public StudentAccount save(StudentAccount studentAccount) {
		return studentAccountRepository.save(studentAccount);
	}

}
