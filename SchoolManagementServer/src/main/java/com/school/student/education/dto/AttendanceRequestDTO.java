package com.school.student.education.dto;

import com.school.constant.AttendanceStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRequestDTO {

	private Long studentId;
	private AttendanceStatus status; // Status: "PRESENT", "ABSENT", etc.
	private String remarks;
	
}
