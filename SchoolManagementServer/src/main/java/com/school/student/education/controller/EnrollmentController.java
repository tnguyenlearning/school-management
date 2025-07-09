package com.school.student.education.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.student.education.entity.Course;
import com.school.student.education.service.EnrollmentService;
import com.school.student.enrollment.dto.EnrollmentDTO;
import com.school.student.enrollment.dto.StudentEnrollmentDTO;
import com.school.student.enrollment.entity.Enrollment;
import com.school.student.enrollment.entity.Student;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/enrollments")
@RequiredArgsConstructor

@Validated
public class EnrollmentController {

	private final EnrollmentService enrollmentService;

	@GetMapping("/courses/{courseId}/studentsWithEnrollments")
	public ResponseEntity<List<StudentEnrollmentDTO>> getStudentsAndEnrollmentByCourseId(@PathVariable Long courseId) {

		List<StudentEnrollmentDTO> students = enrollmentService.getStudentsAndEnrollmentByCourseId(courseId);
		return ResponseEntity.ok(students);
	}

	@GetMapping("/{enrollmentId}/studentsWithEnrollments")
	public ResponseEntity<StudentEnrollmentDTO> getStudentsAndEnrollmentByenrollmentId(
			@PathVariable Long enrollmentId) {

		StudentEnrollmentDTO students = enrollmentService.getStudentAndEnrollmentByEnrollmentId(enrollmentId);
		return ResponseEntity.ok(students);
	}

	@GetMapping("/courses/{courseId}/students")
	@ResponseStatus(HttpStatus.OK)
	public List<Student> getStudentsByCourseId(@PathVariable Long courseId) {
		List<Student> students = enrollmentService.getStudentsByCourseId(courseId);

		return students;
	}

	@GetMapping("/students/{studentId}/courses")
	@ResponseStatus(HttpStatus.OK)
	public List<Course> getCoursesByStudentId(@PathVariable Long studentId) {
		List<Course> courses = enrollmentService.getCoursesByStudentId(studentId);

		return courses;
	}

	@DeleteMapping("/courses/{courseId}/students/{studentId}")
	public ResponseEntity<Map<String, String>> removeStudentFromCourse(@PathVariable Long courseId,
			@PathVariable Long studentId) {
		enrollmentService.removeStudentFromCourse(courseId, studentId);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Student removed from course successfully.");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/courses/{courseId}/students/{studentId}/enroll")
	public ResponseEntity<Map<String, String>> enrollStudent(@PathVariable Long courseId, @PathVariable Long studentId,
			@Valid @RequestBody EnrollmentDTO enrollmentDTO) {

		String message = enrollmentService.enrollStudentInCourse(courseId, studentId, enrollmentDTO);

		// Prepare a response with a key for the message
		Map<String, String> response = new HashMap<>();
		response.put("message", message);

		if (message.contains("not found") || message.contains("already enrolled")) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}


}
