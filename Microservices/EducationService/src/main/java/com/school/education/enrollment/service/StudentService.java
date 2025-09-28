package com.school.education.enrollment.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.education.clients.BillingServiceClient;
import com.school.education.enrollment.entities.Student;
import com.school.education.enrollment.repository.StudentRepository;
import com.school.utilslibrary.clients.education.dtos.StudentAccountDTO;
import com.school.utilslibrary.constants.StudentAccountType;
import com.school.utilslibrary.exception.NotFoundException;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;
	private final BillingServiceClient billingServiceClient;

	public Student findById(Long studentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new NotFoundException("Student not found"));
		return student;
	}

	@Transactional
	public Student create(Student student) {
		Student createdStudent = studentRepository.save(student);
		
		StudentAccountDTO dto = new StudentAccountDTO();
		dto.setBalanceAmount(BigDecimal.ZERO);
		dto.setStudentCode(createdStudent.getStudentCode());
		dto.setType(StudentAccountType.REGULAR);
		try {
			billingServiceClient.createStudentAccount(dto);
		} catch (FeignException e) {
			throw new RuntimeException("Failed to create Student Account!", e);
		}
		return createdStudent;
	}

}
