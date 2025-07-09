package com.school.education.course.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.school.education.clients.BillingServiceClient;
import com.school.education.clients.dtos.CourseAllowedFrequencyRequest;
import com.school.education.constants.CourseStatus;
import com.school.education.course.dtos.CourseRequest;
import com.school.education.course.entities.Course;
import com.school.education.course.repositories.CourseRepository;
import com.school.education.enrollment.entities.Student;
import com.school.education.validators.StatusValidator;
import com.school.utilslibrary.clients.billing.dtos.AllowedFrequencyRequest;
import com.school.utilslibrary.constants.GlobalConstants;
import com.school.utilslibrary.exception.NotFoundException;

import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

	private final CourseRepository courseRepository;
	// private final EnrollmentService enrollmentService;
	private final ModelMapper modelMapper;

	private final BillingServiceClient billingServiceClient;

	public void validateCourseStatus(Long courseId, CourseStatus newStatus) {
		Course course = findById(courseId);
		StatusValidator.validateCourseStatusTransition(course.getStatus(), newStatus);
	}

//	public void persistEnrollmentStatus(String courseId, CourseStatus newStatus) {
//		List<Enrollment> enrollments = enrollmentService.findAllByCourseId(courseId);
//		StatusValidator.updateEnrollmentStatusesBasedOnCourseStatus(newStatus, enrollments);
//
//	}

	@Transactional
	public Course create(CourseRequest courseRequest, List<Long> frequencyIds) {
		Course course = modelMapper.map(courseRequest, Course.class);
		course.validate();

		Course createdCourse = courseRepository.save(course);

		CourseAllowedFrequencyRequest allowedFreqRequest = new CourseAllowedFrequencyRequest();
		allowedFreqRequest.setAllowedDate(LocalDate.now());
		for (Long freqId : frequencyIds) {
			allowedFreqRequest.setBillingFrequencyOption("/api/billingFrequencyOptions/" + freqId);
			allowedFreqRequest.setCourseId(createdCourse.getId());
			try {
				billingServiceClient.createCourseAllowedFrequency(allowedFreqRequest);
			} catch (FeignException e) {
				throw new RuntimeException("Failed to create course frequency!", e); // This will trigger rollback
			}
		}

		return createdCourse;
	}

	@Transactional
	public Course update(Long courseId, CourseRequest request, List<Long> frequencyIds) {
		Course course = findById(courseId);
		if (course.getStatus() != request.getStatus()) {
//			validateCourseStatus(courseId, request.getStatus());
//			persistEnrollmentStatus(courseId, request.getStatus());
		}

		course = modelMapper.map(request, Course.class);
		course.setId(courseId);
		course.validate();

		List<AllowedFrequencyRequest> allowedFrequencyRequest = new ArrayList<>();
		
		for (Long freqId : frequencyIds) {
			AllowedFrequencyRequest freqRequest = new AllowedFrequencyRequest();
			freqRequest.setAllowedDate(LocalDate.now());
			freqRequest.setBillingFrequencyOptionId(freqId);
			allowedFrequencyRequest.add(freqRequest);
		}
		
		try {
			billingServiceClient.updateCourseAllowedFrequency(courseId, allowedFrequencyRequest);
		} catch (FeignException e) {
			throw new RuntimeException("Failed to create course frequency!", e); // This will trigger rollback
		}

		return courseRepository.save(course);

	}

	public Page<Course> getAllcourses(int page, int size) {
		Page<Course> courses = courseRepository.findAll(PageRequest.of(page - 1, size));

		if (courses.isEmpty()) {
			throw new NotFoundException("No courses found for the specified page and size.");
		}

		return courses;
	}

	public List<Student> searchStudentsByStudentCodeNotEnrolled(String courseId, String studentCode) {
		return courseRepository.findStudentsByStudentCodeNotEnrolled(courseId, studentCode);
	}

	public List<Student> searchStudentsByPhoneNotEnrolled(String courseId, String phone) {
		return courseRepository.findStudentsByPhoneNotEnrolled(courseId, phone);
	}

	public List<Student> searchStudentsByNameNotEnrolled(String courseId, String firstName) {
		return courseRepository.findStudentsByFirstNameNotEnrolled(courseId, firstName);
	}

	public Course mapToEntity(CourseRequest courseRequest) {
		Course course = new Course();
		course.setCode(courseRequest.getCode());
		course.setName(courseRequest.getName());
		course.setDescription(courseRequest.getDescription());
		course.setStatus(courseRequest.getStatus());
		course.setStartDate(courseRequest.getStartDate());
		course.setEndDate(courseRequest.getEndDate());
		course.setStartTime(courseRequest.getStartTime());
		course.setEndTime(courseRequest.getEndTime());
		course.setDaysOfWeek(courseRequest.getDaysOfWeek());
		return course;
	}

	public Course findById(Long courseId) {
		return courseRepository.findById(courseId)
				.orElseThrow(() -> new NotFoundException("Course with ID " + courseId + " not found."));
	}

}
