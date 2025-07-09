package com.school.education.session.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.education.constants.SessionStatus;
import com.school.education.session.entities.Attendance;
import com.school.education.session.entities.ClassSession;
import com.school.education.session.services.ClassSessionService;
import com.school.utilslibrary.restapi.ApiResponse;
import com.school.utilslibrary.restapi.ApiResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/class-sessions")
@RequiredArgsConstructor

public class ClassSessionController {

	private final ClassSessionService classSessionService;

	@PostMapping("courses/{courseId}/generate")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> generateSessions(@PathVariable Long courseId, @RequestParam int monthsAhead) {
		classSessionService.createSessionsForCourse(courseId, monthsAhead);
		return ApiResponseBuilder.success("Generate sessions successfully", null, null);
	}

	@PostMapping("courses/{courseId}/add-session")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> addManualSession(@PathVariable Long courseId, @RequestBody ClassSession request) {
		classSessionService.addManualSession(courseId, request);
		return ApiResponseBuilder.success("Session added successfully", null, null);
	}

	@PatchMapping("/{sessionId}/status")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> updateSessionStatus(@PathVariable Long sessionId, @RequestParam SessionStatus status) {
		classSessionService.updateSessionStatus(sessionId, status);
		return ApiResponseBuilder.success("Session status updated", null, null);
	}

	@PostMapping("{sessionId}/students/{studentId}/mark")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<Long> markAttendance(@PathVariable Long sessionId, @PathVariable Long studentId,
			@RequestBody Attendance request) {
		classSessionService.markAttendance(sessionId, studentId, request);
		return ApiResponseBuilder.success("Attendance marked successfully.", null, null);

	}

	@PutMapping("/sessions/{sessionId}/mark-bulk")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> markBulkAttendance(@PathVariable Long sessionId, @RequestBody List<Attendance> requests) {
		classSessionService.markAttendancesForSession(sessionId, requests);
		return ApiResponseBuilder.success("Attendance marked successfully for all students", null, null);
	}
	
}
