package com.school.tuition.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.school.constant.InvoiceStatus;
import com.school.constant.RefundStatus;
import com.school.exception.NotFoundException;
import com.school.tuition.entity.Invoice;
import com.school.tuition.entity.Payment;
import com.school.tuition.entity.Refund;
import com.school.tuition.repository.InvoiceRepository;
import com.school.tuition.repository.PaymentRepository;
import com.school.tuition.repository.RefundRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefundService {

	private final RefundRepository refundRepository;
	private final PaymentRepository paymentRepository;
	private final InvoiceRepository invoiceRepository;

	public Refund findById(Long refundId) {
		Refund refund = refundRepository.findById(refundId)
				.orElseThrow(() -> new NotFoundException("Refund not found"));
		return refund;
	}

	public void processRefund(Long paymentId, BigDecimal refundAmount) {
		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new NotFoundException("Payment not found"));

		// Check if refund amount is valid (shouldn't exceed the remaining balance)
		BigDecimal currentAmountPaid = payment.getAmountPaid();
		if (refundAmount.compareTo(payment.getAmountPaid().subtract(currentAmountPaid)) > 0) {
			throw new IllegalArgumentException("Refund amount exceeds the paid amount");
		}

		// Create the refund record
		Refund refund = new Refund();
		refund.setPayment(payment);
		refund.setRefundAmount(refundAmount);
		refund.setRefundDate(LocalDate.now());
		refund.setStatus(RefundStatus.APPROVED); // or whatever status is applicable
		refundRepository.save(refund);

		// Update the payment and invoice totals
		payment.setAmountPaid(payment.getAmountPaid().subtract(refundAmount));
		payment.setTotalRefund(payment.getTotalRefund().add(refundAmount));
		paymentRepository.save(payment);

		// Update the invoice's total refund amount
		Invoice invoice = payment.getInvoice();
//		BigDecimal updatedInvoiceRefundAmount = invoice.getTotalRefund().add(refundAmount);
//		invoice.setTotalRefund(updatedInvoiceRefundAmount);

		// Recalculate invoice status
		BigDecimal totalPaid = null;// = invoice.getTotalPaid().subtract(updatedInvoiceRefundAmount);
		if (totalPaid.compareTo(invoice.getAmountDue()) == 0) {
			invoice.setStatus(InvoiceStatus.PAID);
		} else if (totalPaid.compareTo(BigDecimal.ZERO) == 0) {
			invoice.setStatus(InvoiceStatus.UNPAID);
		} else {
			invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
		}

		invoiceRepository.save(invoice);
	}

//	// 2. Process Payout (Update the paid_out_money and invoice status)
//	public void processPayout(Refund refund, BigDecimal refundAmount) {
//		// Update the paid_out_money in the refund
//		refund.setPaidOutMoney(refundAmount);
//		refundRepository.save(refund);
//
//		// Update the payment's paid_out_money
//		Payment payment = refund.getPayment();
//		BigDecimal updatedPaidOutMoney = payment.getPaidOutMoney().add(refundAmount);
//		payment.setPaidOutMoney(updatedPaidOutMoney);
//		paymentRepository.save(payment);
//
//		// Update invoice status based on paid amounts
//		Invoice invoice = refund.getInvoice();
//		BigDecimal remainingBalance = payment.getAmountPaid().subtract(updatedPaidOutMoney);
//
//		if (remainingBalance.compareTo(BigDecimal.ZERO) == 0) {
//			invoice.setStatus(InvoiceStatus.PAID);
//		} else if (remainingBalance.compareTo(payment.getAmountPaid()) == 0) {
//			invoice.setStatus(InvoiceStatus.UNPAID);
//		} else {
//			invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
//		}
//
//		invoiceRepository.save(invoice);
//
//		// Perform the actual payout to the student (this could be through an external
//		// system)
//		// e.g., externalService.transferFunds(student, refundAmount);
//		System.out.println("Payout of " + refundAmount + " was processed to the student.");
//	}

}
