package com.school.student.education.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.constant.SessionStatus;
import com.school.student.education.dto.SessionRequestDTO;
import com.school.student.education.service.ClassSessionService;
import com.school.utils.dto.notusedApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/class-sessions")
@RequiredArgsConstructor

public class ClassSessionController {

	private final ClassSessionService classSessionService;

	@PostMapping("courses/{courseId}/add-session")
	@ResponseStatus(HttpStatus.OK)
	public notusedApiResponse<Long> addManualSession(@PathVariable Long courseId, @RequestBody SessionRequestDTO request) {
		Long sessionId = classSessionService.addManualSession(courseId, request);
		return notusedApiResponse.success("Session added", sessionId);
	}

	@PatchMapping("/{sessionId}/status")
	@ResponseStatus(HttpStatus.OK)
	public notusedApiResponse<Long> updateSessionStatus(@PathVariable Long sessionId, @RequestParam SessionStatus status) {
		classSessionService.updateSessionStatus(sessionId, status);
		return notusedApiResponse.success("Session status updated", sessionId);
	}

}
