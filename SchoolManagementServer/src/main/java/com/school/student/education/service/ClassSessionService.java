package com.school.student.education.service;

import org.springframework.stereotype.Service;

import com.school.constant.SessionStatus;
import com.school.student.education.dto.SessionRequestDTO;
import com.school.student.education.entity.ClassSession;
import com.school.student.education.entity.Course;
import com.school.student.education.repository.ClassSessionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassSessionService {

	private final ClassSessionRepository classSessionRepository;
	private final CourseService courseService;

	public Long addManualSession(Long courseId, SessionRequestDTO request) {
		Course course = courseService.findById(courseId);
		ClassSession session = ClassSession.builder()
				.course(course)
				.sessionDate(request.getSessionDate())
				.status(request.getStatus())
				.remark(request.getRemark())
				.adjustedStartTime(request.getAdjustedStartTime())
				.adjustedEndTime(request.getAdjustedEndTime())
				.build();
		ClassSession createdClassSession = classSessionRepository.save(session);

		return createdClassSession.getId();
	}

	public Long updateSessionStatus(Long sessionId, SessionStatus newStatus) {
		ClassSession session = classSessionRepository.findById(sessionId)
				.orElseThrow(() -> new RuntimeException("Session not found"));
		session.setStatus(newStatus);
		ClassSession updatedSession = classSessionRepository.save(session);

		return updatedSession.getId();
	}

}
