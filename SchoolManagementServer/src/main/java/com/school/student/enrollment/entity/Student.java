package com.school.student.enrollment.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.auth.entity.User;
import com.school.constant.Gender;
import com.school.student.education.entity.Assessment;
import com.school.student.education.entity.Attendance;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "STUDENT", uniqueConstraints = { @UniqueConstraint(columnNames = "email", name = "UK_email"),
		@UniqueConstraint(columnNames = "student_code", name = "UK_student_code") })
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Enrollment> enrollments;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Attendance> attendances;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Assessment> assessments;

	@NotBlank(message = "Student Code cannot be empty")
	private String studentCode;

	@NotBlank(message = "Student Code cannot be empty")
	private String firstName;

	@NotBlank(message = "Student Code cannot be empty")
	private String lastName;

	@NotNull(message = "Gender cannot be empty")
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Min(value = 0, message = "Age must be at least 0")
	@Max(value = 100, message = "Age must not exceed 100")
	@NotNull(message = "Age cannot be empty")
	private int age;

	@Email(message = "Must enter email")
	private String email;

	@Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number must be valid (e.g., +1234567890 or 1234567890)")
	@NotBlank(message = "Phone number cannot be empty")
	private String phoneNumber;

	@NotBlank(message = "Address cannot be empty")
	private String address;

}
