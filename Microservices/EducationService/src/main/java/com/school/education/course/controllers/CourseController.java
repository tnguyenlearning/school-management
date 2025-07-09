package com.school.education.course.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.education.clients.BillingServiceClient;
import com.school.education.clients.dto.FrequencyOptionResponseDTO;
import com.school.education.course.entities.Course;
import com.school.education.course.services.CourseService;
import com.school.education.dtos.CourseSetupRequest;
import com.school.utilslibrary.restapi.ApiResponse;
import com.school.utilslibrary.restapi.ApiResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v2/courses")
@RequiredArgsConstructor
public class CourseController {

	private final BillingServiceClient billingServiceClient;

	private final CourseService courseService;

	@GetMapping("/test")
	public FrequencyOptionResponseDTO getEnrollmentsFromEducation() {
		return billingServiceClient.getAllFrequencies();
	}

//	@PostMapping("/generate-range")
//	@ResponseStatus(HttpStatus.OK)
//	public ApiResponse<List<GenerateBillingResponseDTO>> generateRangeBillings(
//			@RequestBody GenerateBillingRequestDTO request) {
//		List<GenerateBillingResponseDTO> generatedBillings = billingCycleService
//				.generateBillingForRangeStudents(request);
//
//		return ApiResponseBuilder.success("courses retrieved successfully", generatedBillings, null);
//
//	}
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<Course> createCourse(@RequestBody CourseSetupRequest courseSetupRequest) {
		Course createdCourse = courseService.create(courseSetupRequest.getCourse(),
				courseSetupRequest.getFrequencyOptionIds());
		return ApiResponseBuilder.success("Course created successfully", createdCourse, null);
	}

	@PutMapping("{courseId}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<Course> updateCourse(@PathVariable Long courseId,
			@RequestBody CourseSetupRequest courseSetupRequest) {
		Course updatedCourse = courseService.update(courseId, courseSetupRequest.getCourse(),
				courseSetupRequest.getFrequencyOptionIds());
		return ApiResponseBuilder.success("Course updated successfully", updatedCourse, null);
	}

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
//
//	@GetMapping("/{courseId}/students/searchByFirstNameNotEnrolled")
//	public ResponseEntity<List<Student>> searchStudentsByNameNotEnrolled(@PathVariable Long courseId,
//			@RequestParam String firstName) {
//
//		List<Student> students = courseService.searchStudentsByNameNotEnrolled(courseId, firstName);
//		return ResponseEntity.ok(students);
//	}

}
