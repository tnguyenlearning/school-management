package com.school.mgmt.billing.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.mgmt.billing.entity.Receipt;
import com.school.mgmt.billing.service.ReceiptService;
import com.school.utilslibrary.restapi.ApiResponse;
import com.school.utilslibrary.restapi.ApiResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v2/receipts")
@RequiredArgsConstructor

public class ReceiptController {

	private final ReceiptService receiptService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/{studentCode}")
	public ApiResponse<Receipt> createReceipt(@PathVariable String studentCode, @Valid @RequestBody Receipt request) {
		Receipt receipt = receiptService.create(studentCode, request);
		return ApiResponseBuilder.success("Success", receipt, null);
	}
	
}
