package com.school.autonum.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.autonum.dto.AssignNumberResponse;
import com.school.autonum.dto.ConfirmNumberRequest;
import com.school.autonum.dto.GenerateNumberRequest;
import com.school.autonum.dto.ReclaimNumberRequest;
import com.school.autonum.service.AutoNumberService;
import com.school.constant.AnumType;
import com.school.utils.response.ApiResponse;
import com.school.utils.response.ApiResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/auto-numbers")
@RequiredArgsConstructor

public class AutoNumberController {

	private final AutoNumberService autoNumberService;

	@PostMapping("/reserve")
	public ApiResponse<?> assignNumber(@RequestBody AnumType type) {
		AssignNumberResponse response = autoNumberService.reserveNumber(type);
		return ApiResponseBuilder.success("Reserve success", response, null);
	}

	@PostMapping("/confirm")
	public ApiResponse<?> confirmNumber(@RequestBody ConfirmNumberRequest request) {
		autoNumberService.deleteReservedNumber(request.getType(), request.getNumber());
		return ApiResponseBuilder.success("Delete number success", null, null);
	}

	@PostMapping("/reclaim")
	public ApiResponse<?> reclaimNumber(@RequestBody ReclaimNumberRequest request) {
		autoNumberService.reclaimReservedNumber(request.getType(), request.getNumber());
		return ApiResponseBuilder.success("Reclaim success", null, null);
	}

	@PostMapping("/generate")
	public ApiResponse<?> generateNumbers(@RequestBody GenerateNumberRequest request) {
		autoNumberService.generateNumbers(request.getType(), request.getPrefix(), request.getCount());
		return ApiResponseBuilder.success("Generate success", null, null);
	}

}