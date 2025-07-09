package com.school.education.enrollment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.education.clients.BillingServiceClient;
import com.school.education.course.entities.Course;
import com.school.education.course.services.CourseService;
import com.school.education.enrollment.dto.EnrollmentRequestDTO;
import com.school.education.enrollment.dto.StudentEnrollmentDTO;
import com.school.education.enrollment.entities.Enrollment;
import com.school.education.enrollment.entities.Student;
import com.school.education.enrollment.repositories.EnrollmentRepository;
import com.school.utilslibrary.clients.billing.dtos.BillingHeaderDTO;
import com.school.utilslibrary.clients.billing.dtos.DataForBillingDTO;
import com.school.utilslibrary.clients.billing.dtos.DataForBillingRequest;
import com.school.utilslibrary.constants.GlobalConstants;
import com.school.utilslibrary.exception.BadRequestException;
import com.school.utilslibrary.exception.NotFoundException;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

	private final EnrollmentRepository enrollmentRepository;
	private final CourseService courseService;
	private final StudentService studentService;
	private final BillingServiceClient billingServiceClient;

	@Transactional
	public Enrollment enroll(EnrollmentRequestDTO request) {
		Course course = courseService.findById(request.getCourseId());
		request.setEndDate(request.getEndDate() != null ? request.getEndDate() : GlobalConstants.MAX_DATE);
		if (course.getStartDate().isAfter(request.getStartDate())
				|| course.getStartDate().isAfter(request.getEnrollmentDate())) {
			throw new BadRequestException("Start Date and Enrollment Date must be before or equal Course start date");
		}
		if (course.getEndDate().isAfter(request.getEndDate())) {
			throw new BadRequestException("End Date must be before or equal Course end date");
		}
		Student student = studentService.findById(request.getStudentId());
		Enrollment enrollment = new Enrollment();
		enrollment.setStudent(student);
		enrollment.setCourse(course);
		enrollment.setStatus(request.getStatus());
		enrollment.setEnrollmentDate(request.getEnrollmentDate());
		enrollment.setStartDate(request.getStartDate());
		enrollment.setEndDate(request.getEndDate());
		enrollment.validate();

		Enrollment createdEnrollment = enrollmentRepository.save(enrollment);

		BillingHeaderDTO billingHeaderDTO = new BillingHeaderDTO();
		billingHeaderDTO.setEnrollmentId(createdEnrollment.getId());
		billingHeaderDTO.setStudentCode(student.getStudentCode());
		billingHeaderDTO
				.setBillingFrequencyOption("/api/billingFrequencyOptions/" + request.getBillingFrequencyOptionId());
		billingHeaderDTO.setNextBillingDate(createdEnrollment.getStartDate());
		try {
			billingServiceClient.createBillingHeader(billingHeaderDTO);
		} catch (FeignException e) {
			throw new RuntimeException("Failed to create course frequency!", e); // This will trigger rollback
		}
		return createdEnrollment;
	}

	@Transactional
	public Enrollment update(Long enrollmentId, EnrollmentRequestDTO enrollmentRequestDTO) {
		Enrollment existingEnrollment = findById(enrollmentId);
		Student student = studentService.findById(enrollmentRequestDTO.getStudentId());
		existingEnrollment.setStudent(student);
		existingEnrollment.setCourse(courseService.findById(enrollmentRequestDTO.getCourseId()));
		existingEnrollment.setStatus(enrollmentRequestDTO.getStatus());
		existingEnrollment.setEnrollmentDate(enrollmentRequestDTO.getEnrollmentDate());
		existingEnrollment.setStartDate(enrollmentRequestDTO.getStartDate());
		existingEnrollment.setEndDate(enrollmentRequestDTO.getEndDate() != null ? enrollmentRequestDTO.getEndDate()
				: GlobalConstants.MAX_DATE);
		return enrollmentRepository.save(existingEnrollment);
	}

	public List<StudentEnrollmentDTO> findStudentsAndEnrollmentByCourseId(Long courseId) {
		List<StudentEnrollmentDTO> studentEnrollmentDTOs = new ArrayList<>();

		Course course = courseService.findById(courseId);
		List<Enrollment> enrollments = course.getEnrollments();

		for (Enrollment e : enrollments) {
			Student student = e.getStudent();
			studentEnrollmentDTOs.add(new StudentEnrollmentDTO(student.getId(), student.getStudentCode(),
					student.getFirstName(), student.getLastName(), student.getGender(), student.getAge(),
					student.getPhoneNumber(), student.getEmail(), student.getAddress(), e.getId(),
					e.getEnrollmentDate(), e.getStartDate(), e.getEndDate(), e.getStatus()));
		}

		return studentEnrollmentDTOs;
	}

	public Enrollment findById(Long enrollmentId) {
		return enrollmentRepository.findById(enrollmentId)
				.orElseThrow(() -> new NotFoundException("Enrollment not found"));
	}

	public List<?> getDataForBilling(DataForBillingRequest request) {
		List<DataForBillingDTO> billingDTOs = new ArrayList<>();
		List<Enrollment> enrollments = enrollmentRepository.findAllById(request.getEnrollmentIds());

		for (Enrollment e : enrollments) {
			DataForBillingDTO dto = new DataForBillingDTO();
			dto.setEnrollmentId(e.getId());
			dto.setCourseCode(e.getCourse().getCode());
			dto.setStudentCode(e.getStudent().getStudentCode());
			dto.setStatus(e.getStatus());
			dto.setEndDate(e.getEndDate());
			dto.setLearningDays(e.getCourse().getDaysOfWeek());
			billingDTOs.add(dto);
		}
		return billingDTOs;
	}
}
