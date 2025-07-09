package com.school.student.enrollment.dto;

import java.time.LocalDate;

import com.school.constant.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentUserRequestDTO {

	private String studentCode;

	private String firstName;

	private String lastName;

	private Gender gender;

	private int age;
	
	private String email;

	private String phoneNumber;

	private String address;

	private String username;

	private String password;

	private String confirmPassword;

}
