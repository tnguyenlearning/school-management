package com.school.mgmt.billing.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.mgmt.billing.entity.Invoice;
import com.school.mgmt.billing.repository.InvoiceRepository;
import com.school.utilslibrary.constant.billing.InvoiceStatus;
import com.school.utilslibrary.constant.billing.InvoiceType;
import com.school.utilslibrary.restapi.ApiResponse;
import com.school.utilslibrary.restapi.ApiResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v2/invoices")
@RequiredArgsConstructor

public class InvoiceController {
	private final InvoiceRepository invoiceRepository;
	
	@GetMapping("/search/searchInvoices")
	public ApiResponse<Page<List<Invoice>>> findStudentCodeContaining(
			@Param("studentCode") String studentCode,
			@Param("courseCode") String courseCode,
			@Param("billingCycleId") Long billingCycleId,
			@Param("issueDate") LocalDate issueDate,
			@Param("publicDate") LocalDate publicDate,
			@Param("status") InvoiceStatus status,
			@Param("type") InvoiceType type, 
			Pageable pageable) {
		
		return ApiResponseBuilder.success("Success",
				invoiceRepository.searchInvoices(studentCode,courseCode,billingCycleId,issueDate,publicDate,status,type, pageable), null);
	}
	
	@GetMapping("/search/searchInvoicesNoPage")
	public ApiResponse<List<Invoice>> findStudentCodeContainingaa(
			@Param("studentCode") String studentCode,
			@Param("courseCode") String courseCode,
			@Param("billingCycleId") Long billingCycleId,
			@Param("issueDate") LocalDate issueDate,
			@Param("publicDate") LocalDate publicDate,
			@Param("status") InvoiceStatus status,
			@Param("type") InvoiceType type, 
			Pageable pageable) {
		
		return ApiResponseBuilder.success("Success",
				invoiceRepository.searchInvoicesNoPage(studentCode,courseCode,billingCycleId,issueDate,publicDate,status,type), null);
	}
	

	
//	@GetMapping("/search")
//	public List<Invoice> searchInvoices(
//			@RequestParam(required = false) String studentCode,
//			@RequestParam(required = false) Long billingCycleId,
//			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate issueDate,
//			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publicDate,
//			@RequestParam(required = false) InvoiceStatus status,
//			@RequestParam(required = false) InvoiceType type) {
//		return invoiceRepository.searchInvoices(studentCode, billingCycleId, issueDate, publicDate, status, type);
//	}

//	private final InvoiceService invoiceService;
//
//	private final ReceiptService receiptService;
//
//	private final PaymentService paymentService;
//
//	private final RefundService refundService;
//
////	// Create new invoice
////	@PostMapping
////	public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceRequestDTO invoiceRequest) {
////		Invoice invoice = invoiceService.createInvoice(invoiceRequest);
////		return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
////	}
////
////	// Get all invoices with a specific status
////	@GetMapping("/status/{status}")
////	public ResponseEntity<List<Invoice>> getInvoicesByStatus(@PathVariable InvoiceStatus status) {
////		List<Invoice> invoices = invoiceService.getInvoicesByStatus(status);
////		return ResponseEntity.ok(invoices);
////	}
//
//	// Record a receipt for a student
//	@PostMapping("/receipts")
//	public ResponseEntity<Receipt> recordReceipt(@RequestBody ReceiptRequestDTO receiptRequest) {
//		Receipt receipt = receiptService.createReceipt(receiptRequest);
//		return ResponseEntity.status(HttpStatus.CREATED).body(receipt);
//	}
//
////	// Record a payment for an invoice
////	@PostMapping("/payments")
////	public ResponseEntity<Payment> recordPayment(@RequestBody PaymentRequestDTO paymentRequest) {
////		Payment payment = paymentService.createPayment(paymentRequest);
////		return ResponseEntity.status(HttpStatus.CREATED).body(payment);
////	}
////
////	// Issue a refund
////	@PostMapping("/refunds")
////	public ResponseEntity<Long> issueRefund(@RequestBody RefundRequestDTO refundRequest) {
////		refundService.processRefund(refundRequest);
////		return ResponseEntity.status(HttpStatus.CREATED).body("success");
////	}
////
////	// Update invoice status based on payments
////	@PutMapping("/{id}/update-status")
////	public ResponseEntity<Invoice> updateInvoiceStatus(@PathVariable Long id) {
////		invoiceService.updateInvoiceStatus(id);
////		return ResponseEntity.ok().build();
////	}

}
