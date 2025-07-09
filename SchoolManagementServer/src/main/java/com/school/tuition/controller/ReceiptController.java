package com.school.tuition.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.tuition.service.ReceiptService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/receipts")
@RequiredArgsConstructor

public class ReceiptController {

	private final ReceiptService receiptService;

}
