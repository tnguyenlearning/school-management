package com.school.student.education.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.student.education.dto.CourseRequest;
import com.school.student.education.entity.Course;
import com.school.student.education.repository.CourseRepository;
import com.school.student.education.service.CourseService;
import com.school.student.enrollment.entity.Student;
import com.school.utils.response.ApiResponse;
import com.school.utils.response.ApiResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/courses")
@RequiredArgsConstructor

public class CourseController {

	private final CourseService courseService;
	private final CourseRepository courseRepository;

	@PostMapping
	public ApiResponse<?> createCourse(@RequestBody Course course) {
		  if (course.getFrequencyOptions() != null) {
	            course.getFrequencyOptions().forEach(option -> option.setCourse(course));
	        }
	        Course savedCourse = courseRepository.save(course);
		return ApiResponseBuilder.success("Courses create successfully", savedCourse, null);
	}

//	@PostMapping
//	public ApiResponse<?> createCourse(@Valid @RequestBody CourseRequest courseRequest) {
//		Course createdCourse = courseService.saveCourse(courseRequest);
//		return ApiResponseBuilder.success("Courses create successfully", createdCourse, null);
//
//	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> getAllcourses(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<Course> courses = courseService.getAllcourses(page, size);

		Map<String, Object> meta = Map.of("page", courses.getNumber() + 1, "size", courses.getSize(), "total",
				courses.getTotalElements());

		return ApiResponseBuilder.success("courses retrieved successfully", courses.getContent(), meta);

	}

	@PutMapping("/{courseId}")
	public ApiResponse<?> updateCourse(@PathVariable long courseId, @Valid @RequestBody Course course) {
		if (course.getFrequencyOptions() != null) {
			course.getFrequencyOptions().forEach(option -> option.setCourse(course));
        }
		courseService.updateCourse(courseId, course);
		return ApiResponseBuilder.success("Course updated successfully", null, null);
	}

//	@PostMapping("/{courseId}/sessions/generate")
//	@ResponseStatus(HttpStatus.OK)
//	public ApiResponse<?> generateSessions(@PathVariable Long courseId, @RequestParam int monthsAhead) {
//		courseService.createSessionsForCourse(courseId, monthsAhead);
//		return ApiResponseBuilder.success("Course updated successfully", null, null);
//	}
//
//	@GetMapping("/{courseId}/students/searchByStudentCodeNotEnrolled")
//	public ResponseEntity<List<Student>> searchStudentsByStudentCodeNotEnrolled(@PathVariable Long courseId,
//			@RequestParam String studentCode) {
//
//		List<Student> students = courseService.searchStudentsByStudentCodeNotEnrolled(courseId, studentCode);
//		return ResponseEntity.ok(students);
//	}
//
//	@GetMapping("/{courseId}/students/searchByPhoneNotEnrolled")
//	public ResponseEntity<List<Student>> searchStudentsByPhoneNotEnrolled(@PathVariable Long courseId,
//			@RequestParam String phone) {
//
//		List<Student> students = courseService.searchStudentsByPhoneNotEnrolled(courseId, phone);
//		return ResponseEntity.ok(students);
//	}

//	@GetMapping("/{courseId}/students/searchByFirstNameNotEnrolled")
//	public ResponseEntity<List<Student>> searchStudentsByNameNotEnrolled(@PathVariable Long courseId,
//			@RequestParam String firstName) {
//
//		List<Student> students = courseService.searchStudentsByNameNotEnrolled(courseId, firstName);
//		return ResponseEntity.ok(students);
//	}

}
