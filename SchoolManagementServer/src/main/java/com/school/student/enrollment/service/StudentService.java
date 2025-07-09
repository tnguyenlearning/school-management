package com.school.student.enrollment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.school.auth.dto.RegisterRequestDTO;
import com.school.auth.entity.User;
import com.school.auth.service.UserService;
import com.school.exception.NotFoundException;
import com.school.student.education.entity.Course;
import com.school.student.education.service.CourseService;
import com.school.student.education.service.EnrollmentService;
import com.school.student.enrollment.dto.StudentDTO;
import com.school.student.enrollment.dto.StudentUserRequestDTO;
import com.school.student.enrollment.dto.StudentUserResponseDTO;
import com.school.student.enrollment.entity.Enrollment;
import com.school.student.enrollment.entity.Student;
import com.school.student.enrollment.repository.StudentRepository;
import com.school.tuition.entity.Refund;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;
	private final EnrollmentService enrollmentService;
	private final CourseService courseService;
	private final UserService userService;
	
	public Student findById(Long studentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new NotFoundException("Student not found"));
		return student;
	}


	public Page<Student> getAllStudents(int page, int size) {
		Page<Student> students = studentRepository.findAll(PageRequest.of(page - 1, size));

		if (students.isEmpty()) {
			throw new NotFoundException("No students found for the specified page and size.");
		}

		return students;
	}

	@Transactional
	public StudentUserResponseDTO createStudentWithUser(StudentUserRequestDTO requestDTO) {
//		RegisterRequestDTO user = RegisterRequestDTO.builder()
//				.username(requestDTO.getUsername())
//				.password(requestDTO.getPassword())
//				.confirmPassword(requestDTO.getConfirmPassword())
//				.build();
//		
//		User createdUser = userService.create(user);

		Student student = Student.builder().studentCode(requestDTO.getStudentCode())
				.firstName(requestDTO.getFirstName()).lastName(requestDTO.getLastName()).gender(requestDTO.getGender())
				.email(requestDTO.getEmail()).phoneNumber(requestDTO.getPhoneNumber()).address(requestDTO.getAddress())
				.age(requestDTO.getAge())

				.build();

		Student createdStudent = studentRepository.save(student);

		return new StudentUserResponseDTO(Long.getLong("1"), createdStudent.getStudentCode());

	}

}
