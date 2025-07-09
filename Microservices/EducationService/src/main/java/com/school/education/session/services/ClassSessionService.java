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

		LocalDate currentDate = course.getStartDate();
		LocalDate endDate = course.getEndDate() != null ? course.getEndDate() : currentDate.plusMonths(monthsAhead);

		while (!currentDate.isAfter(endDate)) {
			if (course.getDaysOfWeek().contains(currentDate.getDayOfWeek())) {
				// Check if the session already exists for this course and session date
				Optional<ClassSession> existingSession = classSessionRepository.findByCourseAndSessionDate(course,
						currentDate);

				if (!existingSession.isPresent()) {
					// Create a new ClassSession if it doesn't exist
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

	public Long updateSessionStatus(Long sessionId, SessionStatus newStatus) {
		ClassSession session = classSessionRepository.findById(sessionId)
				.orElseThrow(() -> new RuntimeException("Session not found"));
		session.setStatus(newStatus);
		ClassSession updatedSession = classSessionRepository.save(session);

		return updatedSession.getId();
	}
	


	public void markAttendance(Long sessionId, Long studentId, Attendance request) {
		ClassSession session = classSessionRepository.findById(sessionId)
				.orElseThrow(() -> new RuntimeException("Session not found"));
		/* Call API to check student id */

		Attendance attendance = attendanceRepository.findByClassSessionAndStudentId(session, studentId)
				.orElse(new Attendance());

		attendance.setClassSession(session);
		attendance.setStudentId(studentId);
		attendance.setStatus(request.getStatus());
		attendance.setRemarks(request.getRemarks());
		
		attendanceRepository.save(attendance);
	}

	@Transactional
	public void markAttendancesForSession(Long sessionId, List<Attendance> requests) {
		ClassSession session = classSessionRepository.findById(sessionId)
				.orElseThrow(() -> new RuntimeException("Session not found"));
		
		List<Attendance> attendances = new ArrayList<>();
		for (Attendance request : requests) {
			/* Call API to check student id */
			
			Attendance attendance = attendanceRepository.findByClassSessionAndStudentId(session, request.getStudentId())
					.orElse(new Attendance());

			attendance.setClassSession(session);
			attendance.setStudentId(request.getStudentId());
			attendance.setStatus(request.getStatus());
			attendance.setRemarks(request.getRemarks());

			attendances.add(attendance);
		}

		// Perform batch update
		// You can directly save all the attendance records with EntityManager
		for (Attendance attendance : attendances) {
			entityManager.merge(attendance); // Merge updates into the session
		}
	}

}
