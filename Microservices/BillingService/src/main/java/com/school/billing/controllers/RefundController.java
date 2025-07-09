package com.school.billing.controllers;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.billing.entities.Refund;
import com.school.billing.services.RefundService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/refunds")
@RequiredArgsConstructor

public class RefundController {

	private final RefundService refundService;

//	// Endpoint to process a refund (create the refund record)
//	@PostMapping("/processRefund")
//	public ResponseEntity<Refund> processRefund(@RequestParam String paymentId, @RequestParam String invoiceId,
//			@RequestParam BigDecimal refundAmount) {
//
//		Refund refund = refundService.processRefund(paymentId, invoiceId, refundAmount);
//		return ResponseEntity.ok(refund);
//	}
//
//	// Endpoint to handle the payout (transfer money to student and update status)
//	@PostMapping("/processPayout")
//	public ResponseEntity<Long> processPayout(@RequestParam String refundId, @RequestParam BigDecimal refundAmount) {
//
//		Refund refund = refundService.findById(refundId);
//
//		// Process the payout and update the statuses
//		refundService.processPayout(refund, refundAmount);
//		return ResponseEntity.ok("Payout processed successfully");
//	}
}
