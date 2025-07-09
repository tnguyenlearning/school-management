package com.school.student.education.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.student.education.dto.AttendanceRequestDTO;
import com.school.student.education.service.AttendanceService;
import com.school.utils.dto.notusedApiResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/attendances")
@RequiredArgsConstructor

public class AttendanceController {

	private final AttendanceService attendanceService;

	@PutMapping("/sessions/{sessionId}/students/{studentId}/mark")
	@ResponseStatus(HttpStatus.OK)
	public notusedApiResponse<Long> markAttendance(@PathVariable Long sessionId, @PathVariable Long studentId,
			@RequestBody AttendanceRequestDTO request) {
		Long id = attendanceService.markAttendance(sessionId, studentId, request);
		return notusedApiResponse.success("Attendance marked.", id);

	}

	@PostMapping("/hello")
	public String hello(@RequestBody List<String> attendanceRequests) {
		String message = "Received: " + String.join(", ", attendanceRequests);
		return message;
	}

}
