package com.school.student.education.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.school.constant.CourseStatus;
import com.school.constant.ErrorMessage;
import com.school.constant.GlobalConstants;
import com.school.constant.SessionStatus;
import com.school.exception.NotFoundException;
import com.school.student.education.dto.CourseRequest;
import com.school.student.education.dto.FrequencyOptionRequest;
import com.school.student.education.entity.ClassSession;
import com.school.student.education.entity.Course;
import com.school.student.education.repository.ClassSessionRepository;
import com.school.student.education.repository.CourseRepository;
import com.school.student.enrollment.entity.Enrollment;
import com.school.student.enrollment.entity.Student;
import com.school.tuition.entity.CourseFrequencyOption;
import com.school.tuition.repository.CourseFrequencyOptionRepository;
import com.school.validator.StatusValidator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

	private final CourseRepository courseRepository;
	private final EnrollmentService enrollmentService;
	private final ClassSessionRepository classSessionRepository;
	private final CourseFrequencyOptionRepository frequencyOptionRepository;
	private final ModelMapper modelMapper;

	public void validateCourseStatus(Long courseId, CourseStatus newStatus) {
		Course course = findById(courseId);
		StatusValidator.validateCourseStatusTransition(course.getStatus(), newStatus);
	}

	public void persistEnrollmentStatus(Long courseId, CourseStatus newStatus) {
		List<Enrollment> enrollments = enrollmentService.findAllByCourseId(courseId);
		StatusValidator.updateEnrollmentStatusesBasedOnCourseStatus(newStatus, enrollments);

	}

	@Transactional
	public Course saveCourse(CourseRequest courseRequest) {
		Course course = mapToEntity(courseRequest);
		if (course.getEndDate() == null) {
			course.setEndDate(GlobalConstants.MAX_DATE);
		}
		course.validate();
		return courseRepository.save(course);
	}

	@Transactional
	public Course createCourse(Course course) {
		if (course.getEndDate() == null) {
			course.setEndDate(GlobalConstants.MAX_DATE);
		}
		course.validate();

		List<CourseFrequencyOption> frequencyOptions = course.getFrequencyOptions();
		if (frequencyOptions != null) {
			for (CourseFrequencyOption option : frequencyOptions) {
				course.addFrequencyOption(option);
			}
		}
		course.setFrequencyOptions(null);
		Course savedCourse = courseRepository.save(course);

		return savedCourse;
	}

//	@Transactional
//	public Course createCourse(Course course, List<Frequency> allowedFrequencies) {
//		List<CourseFrequencyOption> frequencyOptions = allowedFrequencies.stream().map(frequency -> {
//			CourseFrequencyOption options = new CourseFrequencyOption();
//			options.setCourse(course);
//			options.setFrequency(frequency);
//
////	        // Example calculation logic for feeAmount
////	        switch (frequency) {
////	            case MONTHLY:
////	                options.setFeeAmount(course.getFeeAmount().divide(BigDecimal.valueOf(12), BigDecimal.ROUND_HALF_UP));
////	                options.setTotalLearningDays(30); // Example learning days for monthly
////	                break;
////	            case ANNUAL:
////	                options.setFeeAmount(course.getFeeAmount());
////	                options.setTotalLearningDays(365);
////	                break;
////	            case PER_CLASS:
////	                options.setFeeAmount(course.getFeeAmount().divide(BigDecimal.valueOf(course.getTotalLearningDays()), BigDecimal.ROUND_HALF_UP));
////	                options.setTotalLearningDays(1);
////	                break;
////	            // Add other cases as needed
////	        }
//			return options;
//		}).collect(Collectors.toList());
//
//		course.setFrequencyOptions(frequencyOptions);
//		return courseRepository.save(course);
//	}

	@Transactional
	public Course updateCourse(long courseId, Course request) {
		Course course = findById(courseId);
		request.validate();
		if (course.getStatus() != request.getStatus()) {
			validateCourseStatus(courseId, request.getStatus());
			persistEnrollmentStatus(courseId, request.getStatus());
		}

		course = modelMapper.map(request, Course.class);
		course.setId(courseId);
		return courseRepository.save(course);
	}

	// 3. Modify frequency options only
	public Course modifyFrequencyOptions(Long courseId, List<CourseFrequencyOption> updatedFrequencyOptions) {
		Course existingCourse = courseRepository.findById(courseId)
				.orElseThrow(() -> new NotFoundException("Course not found with id: " + courseId));

		existingCourse.getFrequencyOptions().clear();
		existingCourse.getFrequencyOptions().addAll(updatedFrequencyOptions);

		// Cascade the changes by persisting the course
		return courseRepository.save(existingCourse);
	}

	// 4. Modify days of the week only
	public Course modifyDaysOfWeek(Long courseId, Set<DayOfWeek> updatedDaysOfWeek) {
		Course existingCourse = courseRepository.findById(courseId)
				.orElseThrow(() -> new NotFoundException("Course not found with id: " + courseId));

		existingCourse.getDaysOfWeek().clear();
		existingCourse.getDaysOfWeek().addAll(updatedDaysOfWeek);

		return courseRepository.save(existingCourse);
	}

	public Course findById(Long id) {
		Optional<Course> dbCourse = this.courseRepository.findById(id);

		if (dbCourse.isPresent()) {
			return dbCourse.get();
		}

		throw new NotFoundException(ErrorMessage.COURSE_NOT_EXIST.getValue());
	}

	public Page<Course> getAllcourses(int page, int size) {
		Page<Course> courses = courseRepository.findAll(PageRequest.of(page - 1, size));

		if (courses.isEmpty()) {
			throw new NotFoundException("No courses found for the specified page and size.");
		}

		return courses;
	}

	public void createSessionsForCourse(Long courseId, int monthsAhead) {
		Course course = this.findById(courseId);

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

//	public List<Student> searchStudentsByStudentCodeNotEnrolled(Long courseId, String studentCode) {
//		return courseRepository.findStudentsByStudentCodeNotEnrolled(courseId, studentCode);
//	}
//
//	public List<Student> searchStudentsByPhoneNotEnrolled(Long courseId, String phone) {
//		return courseRepository.findStudentsByPhoneNotEnrolled(courseId, phone);
//	}
//
//	public List<Student> searchStudentsByNameNotEnrolled(Long courseId, String firstName) {
//		return courseRepository.findStudentsByFirstNameNotEnrolled(courseId, firstName);
//	}

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

		// Clear existing frequency options to handle updates
		if (course.getFrequencyOptions() != null) {
			course.getFrequencyOptions().clear();
		}

		// Add frequency options using the helper method
		if (courseRequest.getFrequencyOptions() != null) {
			for (FrequencyOptionRequest optionRequest : courseRequest.getFrequencyOptions()) {
				CourseFrequencyOption option = new CourseFrequencyOption();
				option.setFrequency(optionRequest.getFrequency());
				option.setTotalLearningDays(optionRequest.getTotalLearningDays());
				option.setFeeAmount(optionRequest.getFeeAmount());
				course.addFrequencyOption(option); // Use the helper method here
			}
		}
		return course;
	}

}
