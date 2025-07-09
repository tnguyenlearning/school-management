package com.school;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.student.education.dto.AttendanceRequestDTO;
import com.school.student.education.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/batch")
@RequiredArgsConstructor

public class BatchController {

	private final AttendanceService attendanceService;

	@PutMapping("/sessions/{sessionId}/mark-bulk")
	public ResponseEntity<?> markBulkAttendance(@PathVariable Long sessionId,
			@RequestBody List<AttendanceRequestDTO> attendanceRequests) {
		attendanceService.markAttendancesForSession(sessionId, attendanceRequests);
		Map<String, String> response = new HashMap<>();
		response.put("success", "Attendance marked successfully for all students.");
		return ResponseEntity.ok(response);
	}

}
