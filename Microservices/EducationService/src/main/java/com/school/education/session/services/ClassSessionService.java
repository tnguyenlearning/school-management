package com.school.education.session.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.school.education.constants.SessionStatus;
import com.school.education.course.entities.Course;
import com.school.education.course.services.CourseService;
import com.school.education.session.entities.Attendance;
import com.school.education.session.entities.ClassSession;
import com.school.education.session.repositories.AttendanceRepository;
import com.school.education.session.repositories.ClassSessionRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassSessionService {

	private final ClassSessionRepository classSessionRepository;
	private final CourseService courseService;
	private final AttendanceRepository attendanceRepository;
	private final EntityManager entityManager;

	public void createSessionsForCourse(Long courseId, int monthsAhead) {
		Course course = courseService.findById(courseId);
		Optional<ClassSession> lastSessionOpt = classSessionRepository.findTopByCourseOrderBySessionDateDesc(course);

		LocalDate currentDate;
		if (lastSessionOpt.isPresent()) {
			currentDate = lastSessionOpt.get().getSessionDate().plusDays(1);
		} else {
			currentDate = course.getStartDate();
		}

		LocalDate courseEndDate = course.getEndDate();
		LocalDate targetDate = currentDate.plusMonths(monthsAhead);
		LocalDate endDate = targetDate.isBefore(courseEndDate) || targetDate.isEqual(courseEndDate)
				? targetDate
				: courseEndDate;

		while (!currentDate.isAfter(endDate)) {
			if (course.getDaysOfWeek().contains(currentDate.getDayOfWeek())) {
				Optional<ClassSession> existingSession = classSessionRepository.findByCourseAndSessionDate(course, currentDate);
				if (!existingSession.isPresent()) {
					ClassSession session = new ClassSession();
					session.setCourse(course);
					session.setSessionDate(currentDate);
					session.setStatus(SessionStatus.NOT_STARTED);
					classSessionRepository.save(session);
				}
			}
			currentDate = currentDate.plusDays(1);
		}
	}

	public Long addManualSession(Long courseId, ClassSession request) {
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

}
