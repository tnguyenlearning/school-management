package com.school.tuition.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.school.student.enrollment.entity.Student;
import com.school.student.enrollment.service.StudentService;
import com.school.tuition.dto.ReceiptRequestDTO;
import com.school.tuition.entity.Receipt;
import com.school.tuition.repository.ReceiptRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceiptService {

	private final ReceiptRepository receiptRepository;

	private final StudentService studentService;

	public Receipt createReceipt(ReceiptRequestDTO receiptRequest) {
		Student student = studentService.findById(receiptRequest.getStudentId());

		Receipt receipt = new Receipt();
		receipt.setStudent(student);
		receipt.setAmount(receiptRequest.getAmount());
		receipt.setBalance(receiptRequest.getAmount());
		receipt.setEffdate(receiptRequest.getEffdate());
		return receiptRepository.save(receipt);
	}
	
	public List<Receipt> getReceiptsWithBalanceGreaterThanZero(Long studentId) {
        return receiptRepository.findByStudentIdAndBalanceGreaterThan(studentId, 0.0);
    }
	
	public void saveAll(List<Receipt> receipts) {
        receiptRepository.saveAll(receipts);
	}

}
