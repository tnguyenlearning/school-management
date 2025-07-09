package com.school.education.enrollment.service;

import org.springframework.stereotype.Service;

import com.school.education.enrollment.entities.Student;
import com.school.education.enrollment.repository.StudentRepository;
import com.school.utilslibrary.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;

	public Student findById(Long studentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new NotFoundException("Student not found"));
		return student;
	}

}
