package com.school.education.enrollment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.education.enrollment.repository.StudentRepository;
import com.school.utilslibrary.restapi.ApiResponse;
import com.school.utilslibrary.restapi.ApiResponseBuilder;

import lombok.RequiredArgsConstructor;

@RequestMapping("/v2/students")
@RestController
@RequiredArgsConstructor
public class StudentController {

	private final StudentRepository studentRepository;

	@GetMapping("/search/findStudentCodeContaining")
	public ApiResponse<List<String>> findStudentCodeContaining(@RequestParam(required = false) String phone,
			@RequestParam(required = false) String studentCode, @RequestParam(required = false) String firstName) {
		return ApiResponseBuilder.success("Success",
				studentRepository.findStudentCodeContaining(phone, studentCode, firstName), null);
	}
	
}
