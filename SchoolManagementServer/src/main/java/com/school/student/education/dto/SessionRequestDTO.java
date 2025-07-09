package com.school.student.education.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.school.constant.SessionStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionRequestDTO {
	
	private String remark;

	private LocalDate sessionDate;

	private LocalTime adjustedStartTime;
	private LocalTime adjustedEndTime;

	@Enumerated(EnumType.STRING)
	private SessionStatus status; // NOT_STARTED, IN_PROGRESS, COMPLETED, CANCELED, MISSED, MAKEUP, EXTRA

}
