package com.school.student.education.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.school.constant.BillingAction;
import com.school.constant.EnrollmentStatus;
import com.school.constant.GlobalConstants;
import com.school.exception.BadRequestException;
import com.school.exception.NotFoundException;
import com.school.student.education.entity.Course;
import com.school.student.education.repository.CourseRepository;
import com.school.student.enrollment.dto.EnrollmentDTO;
import com.school.student.enrollment.dto.StudentEnrollmentDTO;
import com.school.student.enrollment.entity.Enrollment;
import com.school.student.enrollment.entity.Student;
import com.school.student.enrollment.repository.EnrollmentRepository;
import com.school.student.enrollment.repository.StudentRepository;
import com.school.tuition.entity.CourseFrequencyOption;
import com.school.tuition.repository.CourseFrequencyOptionRepository;
import com.school.tuition.service.BillingCycleService;
import com.school.validator.StatusValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

	private final EnrollmentRepository enrollmentRepository;
	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private BillingCycleService billingCycleService;
	private final CourseFrequencyOptionRepository courseFrequencyOptionRepository;

	@Autowired
	public void setEnrollmentService(@Lazy BillingCycleService billingCycleService) {
		this.billingCycleService = billingCycleService;
	}

	public List<StudentEnrollmentDTO> getStudentsAndEnrollmentByCourseId(Long courseId) {
		return enrollmentRepository.findStudentsAndEnrollmentByCourseId(courseId);
	}

	public StudentEnrollmentDTO getStudentAndEnrollmentByEnrollmentId(Long enrollmentId) {
		return enrollmentRepository.findStudentAndEnrollmentByEnrollmentId(enrollmentId);
	}

	public List<Course> getCoursesByStudentId(Long studentId) {
		List<Course> courses = enrollmentRepository.findCoursesByStudentId(studentId);
		if (courses.isEmpty()) {
			throw new NotFoundException("Student with ID " + studentId + " not found or has no enrolled courses.");
		}
		return courses;
	}

	public List<Student> getStudentsByCourseId(Long courseId) {
		List<Student> students = enrollmentRepository.findStudentsByCourseId(courseId);
		if (students.isEmpty()) {
			throw new NotFoundException("Course with ID " + courseId + " not found or has no enrolled students.");
		}
		return students;
	}

	public void removeStudentFromCourse(Long courseId, Long studentId) {
		int rowsAffected = enrollmentRepository.deleteByCourseIdAndStudentId(courseId, studentId);
		if (rowsAffected == 0) {
			throw new NotFoundException(
					"No enrollment found for course ID " + courseId + " and student ID " + studentId);
		}
	}

	public String enrollStudentInCourse(Long courseId, Long studentId, EnrollmentDTO request) {
		Optional<Course> courseOpt = courseRepository.findById(courseId);
		Optional<Student> studentOpt = studentRepository.findById(studentId);

		if (StringUtils.isEmpty(request.getStatus().name()) || request.getEnrollmentDate() == null) {
			throw new BadRequestException("Status and Enrollment Date is required.");

		}

		if (!courseOpt.isPresent()) {
			throw new NotFoundException("Course with ID " + courseId + " not found.");
		}

		if (!studentOpt.isPresent()) {
			throw new NotFoundException("Student with ID " + courseId + " not found.");
		}

		Course course = courseOpt.get();
		Student student = studentOpt.get();

		request.validate();
		StatusValidator.validateEnrollmentStatusTransition(course.getStatus(), null, request.getStatus());

		if (enrollmentRepository.findByCourseIdAndStudentId(courseId, studentId).isPresent()) {
			return "Student is already enrolled in this course.";
		}

		Enrollment enrollment = Enrollment.builder().student(student).course(course)
				.enrollmentDate(request.getEnrollmentDate()).startDate(request.getStartDate())
				.endDate(request.getEndDate() != null ? request.getEndDate() : GlobalConstants.MAX_DATE)
				.status(request.getStatus()).frequencyOption(request.getFrequencyOption()).validflag(1).build();
		LocalDate nextGenerate = billingCycleService.calcNextGenerateBillingDate(enrollment, BillingAction.CREATE);
		enrollment.setNextGenerateBillingDate(nextGenerate);
		enrollmentRepository.save(enrollment);

		return "Student enrolled successfully.";
	}

	public List<Enrollment> getFilteredEnrollmentsForBilling(Long fromStudentId, Long toStudentId,
			EnrollmentStatus status, LocalDate effDate) {
		return enrollmentRepository.findByStudentIdRangeDueToGenerateBilling(fromStudentId, toStudentId, status,
				effDate);
	}

	public void save(Enrollment enrollment) {
		enrollmentRepository.save(enrollment);
	}

	public List<Enrollment> findAllByCourseId(Long courseId) {
		return enrollmentRepository.findAllByCourseId(courseId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public void beforeChangeValidate(Enrollment updatedEnrollment) {
		Enrollment originalEnrollment = enrollmentRepository.findById(updatedEnrollment.getId())
				.orElseThrow(() -> new NotFoundException("Enrollment not found"));
		Course course = originalEnrollment.getCourse();

		if (updatedEnrollment.getStatus() != null) {
			StatusValidator.validateEnrollmentStatusTransition(course.getStatus(), originalEnrollment.getStatus(),
					updatedEnrollment.getStatus());
		}

		if (updatedEnrollment.getFrequencyOption() != null) {
			CourseFrequencyOption frequencyOption = courseFrequencyOptionRepository
					.findById(updatedEnrollment.getFrequencyOption().getId())
					.orElseThrow(() -> new NotFoundException("Frequency option not found"));

			if (!frequencyOption.getCourse().equals(course)) {
				throw new BadRequestException("The selected frequency does not match the course.");
			}
		}
	}

}
