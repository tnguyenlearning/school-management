package com.school.student.education.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.school.student.education.dto.AttendanceRequestDTO;
import com.school.student.education.entity.Attendance;
import com.school.student.education.entity.ClassSession;
import com.school.student.education.repository.AttendanceRepository;
import com.school.student.education.repository.ClassSessionRepository;
import com.school.student.enrollment.entity.Student;
import com.school.student.enrollment.repository.StudentRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceService {

	private final ClassSessionRepository classSessionRepository;
	private final StudentRepository studentRepository;
	private final AttendanceRepository attendanceRepository;
	private final EntityManager entityManager;

	public Long markAttendance(Long sessionId, Long studentId, AttendanceRequestDTO request) {
		ClassSession session = classSessionRepository.findById(sessionId)
				.orElseThrow(() -> new RuntimeException("Session not found"));
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new RuntimeException("Student not found"));

		Attendance attendance = attendanceRepository.findByClassSessionAndStudent(session, student)
				.orElse(new Attendance());

		attendance.setClassSession(session);
		attendance.setStudent(student);
//		attendance.setStatus(request.getStatus());
		Attendance changedAttendance = attendanceRepository.save(attendance);
		return changedAttendance.getId();
	}

	@Transactional
	public void markAttendancesForSession(Long sessionId, List<AttendanceRequestDTO> attendanceRequests) {
		// Fetch the session
		ClassSession session = classSessionRepository.findById(sessionId)
				.orElseThrow(() -> new RuntimeException("Session not found"));

		// Prepare a list of attendance entities to be updated
		List<Attendance> attendances = new ArrayList<>();

		for (AttendanceRequestDTO request : attendanceRequests) {
			Long studentId = request.getStudentId();

			Student student = studentRepository.findById(studentId)
					.orElseThrow(() -> new RuntimeException("Student not found"));

			Attendance attendance = attendanceRepository.findByClassSessionAndStudent(session, student)
					.orElse(new Attendance());

			attendance.setClassSession(session);
			attendance.setStudent(student);
			attendance.setStatus(request.getStatus());
			attendance.setRemarks(request.getRemarks());

			// Add to the list for batch update
			attendances.add(attendance);
		}

		// Perform batch update
		// You can directly save all the attendance records with EntityManager
		for (Attendance attendance : attendances) {
			entityManager.merge(attendance); // Merge updates into the session
		}
	}

}
