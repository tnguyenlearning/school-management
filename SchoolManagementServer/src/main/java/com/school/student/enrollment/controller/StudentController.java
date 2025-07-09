package com.school.student.enrollment.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.student.enrollment.dto.StudentUserRequestDTO;
import com.school.student.enrollment.dto.StudentUserResponseDTO;
import com.school.student.enrollment.entity.Student;
import com.school.student.enrollment.service.StudentService;
import com.school.utils.response.ApiResponse;
import com.school.utils.response.ApiResponseBuilder;

import lombok.RequiredArgsConstructor;

@RequestMapping("api/v2/students")
@RestController
@RequiredArgsConstructor

public class StudentController {

	private final StudentService studentService;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.OK)
	public StudentUserResponseDTO createStudentWithUser(@RequestBody StudentUserRequestDTO request) {
		return studentService.createStudentWithUser(request);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> getAllStudents(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		Page<Student> students = studentService.getAllStudents(page, size);

		Map<String, Object> meta = Map.of("page", students.getNumber() + 1, "size", students.getSize(), "total",
				students.getTotalElements());

		return ApiResponseBuilder.success("Students retrieved successfully", students.getContent(), meta);

	}

}
