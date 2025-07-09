package com.school.billing.services;

import org.springframework.stereotype.Service;

import com.school.billing.repositories.InvoiceRepository;
import com.school.billing.repositories.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyPaymentProcessor {

	private final InvoiceRepository invoiceRepository;

	private final PaymentRepository paymentRepository;

//	public void processPayments() {
//		List<Invoice> invoices = invoiceRepository.findAllByStatus(InvoiceStatus.UNPAID);
//
//		for (Invoice invoice : invoices) {
//			BigDecimal totalPayments = BigDecimal.ZERO;//paymentRepository.sumPaymentsByInvoice(invoice.getId());
//
//			// Update invoice status based on payments
//			if (totalPayments.compareTo(invoice.getAmount()) >= 0) {
//				invoice.setStatus(InvoiceStatus.PAID);
//			} else if (totalPayments.compareTo(BigDecimal.ZERO) > 0) {
//				invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
//			}
//
//			invoiceRepository.save(invoice);
//		}
//	}
}
