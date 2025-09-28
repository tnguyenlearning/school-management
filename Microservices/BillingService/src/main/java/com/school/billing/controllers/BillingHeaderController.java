package com.school.billing.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.billing.services.BillingHeaderService;
import com.school.utilslibrary.clients.billing.dtos.BulkNextBillingDateRequest;
import com.school.utilslibrary.restapi.ApiResponse;
import com.school.utilslibrary.restapi.ApiResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v2/billing-headers")
@RequiredArgsConstructor
public class BillingHeaderController {

	private final BillingHeaderService service;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ApiResponse<?> updateBillingInfo(@RequestBody BulkNextBillingDateRequest request) {
		service.bulkUpdateNextBillingDate(request);
		return ApiResponseBuilder.success("Success", "UPDATED", null);
	}

}
