package com.school.tuition.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.constant.InvoiceStatus;
import com.school.tuition.dto.InvoiceRequestDTO;
import com.school.tuition.dto.PaymentRequestDTO;
import com.school.tuition.dto.ReceiptRequestDTO;
import com.school.tuition.dto.RefundRequestDTO;
import com.school.tuition.entity.Invoice;
import com.school.tuition.entity.Payment;
import com.school.tuition.entity.Receipt;
import com.school.tuition.entity.Refund;
import com.school.tuition.service.InvoiceService;
import com.school.tuition.service.PaymentService;
import com.school.tuition.service.ReceiptService;
import com.school.tuition.service.RefundService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/invoices")
@RequiredArgsConstructor

public class InvoiceController {

	private final InvoiceService invoiceService;

	private final ReceiptService receiptService;

	private final PaymentService paymentService;

	private final RefundService refundService;

//	// Create new invoice
//	@PostMapping
//	public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceRequestDTO invoiceRequest) {
//		Invoice invoice = invoiceService.createInvoice(invoiceRequest);
//		return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
//	}
//
//	// Get all invoices with a specific status
//	@GetMapping("/status/{status}")
//	public ResponseEntity<List<Invoice>> getInvoicesByStatus(@PathVariable InvoiceStatus status) {
//		List<Invoice> invoices = invoiceService.getInvoicesByStatus(status);
//		return ResponseEntity.ok(invoices);
//	}

	// Record a receipt for a student
	@PostMapping("/receipts")
	public ResponseEntity<Receipt> recordReceipt(@RequestBody ReceiptRequestDTO receiptRequest) {
		Receipt receipt = receiptService.createReceipt(receiptRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(receipt);
	}

//	// Record a payment for an invoice
//	@PostMapping("/payments")
//	public ResponseEntity<Payment> recordPayment(@RequestBody PaymentRequestDTO paymentRequest) {
//		Payment payment = paymentService.createPayment(paymentRequest);
//		return ResponseEntity.status(HttpStatus.CREATED).body(payment);
//	}
//
//	// Issue a refund
//	@PostMapping("/refunds")
//	public ResponseEntity<Long> issueRefund(@RequestBody RefundRequestDTO refundRequest) {
//		refundService.processRefund(refundRequest);
//		return ResponseEntity.status(HttpStatus.CREATED).body("success");
//	}
//
//	// Update invoice status based on payments
//	@PutMapping("/{id}/update-status")
//	public ResponseEntity<Invoice> updateInvoiceStatus(@PathVariable Long id) {
//		invoiceService.updateInvoiceStatus(id);
//		return ResponseEntity.ok().build();
//	}

}
