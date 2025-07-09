package com.school.student.enrollment.dto;

import java.time.LocalDate;

import com.school.constant.EnrollmentStatus;
import com.school.constant.Gender;
import com.school.student.education.dto.CourseFrequencyOptionDTO;
import com.school.tuition.entity.CourseFrequencyOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentEnrollmentDTO {

	private Long studentId;
	private String studentCode;
	private String firstName;
	private String lastName;
	private Gender gender;
	private int age;
	private String phoneNumber;
	private String email;
	private String address;

	private Long enrollmentId;
	private LocalDate enrollmentDate;
	private LocalDate startDate;
	private LocalDate endDate;
	private EnrollmentStatus enrollmentStatus;
	private CourseFrequencyOptionDTO frequencyOptionDto;

}
