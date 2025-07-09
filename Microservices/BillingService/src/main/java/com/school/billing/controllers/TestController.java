package com.school.billing.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.billing.clients.EducationServiceClient;
import com.school.utilslibrary.clients.education.SearchStudentRequest;

import lombok.RequiredArgsConstructor;

@RequestMapping("/v2/test")
@RestController
@RequiredArgsConstructor
public class TestController {

	private final EducationServiceClient client;

	@GetMapping("/")
	public List<String> test(@RequestParam(required = false) String phone,
			@RequestParam(required = false) String studentCode, @RequestParam(required = false) String firstName) {
		return client.findStudentCodeContaining(phone, studentCode, firstName).getData();
	}
}
