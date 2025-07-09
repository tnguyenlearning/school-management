package com.school.student.education.entity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.school.constant.CourseStatus;
import com.school.exception.BadRequestException;
import com.school.student.enrollment.entity.Enrollment;
import com.school.tuition.entity.CourseFrequencyOption;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COURSE", uniqueConstraints = { @UniqueConstraint(columnNames = "code", name = "UK_code") })
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Enrollment> enrollments = new ArrayList<>();

	@ElementCollection(targetClass = DayOfWeek.class)
	@CollectionTable(name = "course_schedule_days", joinColumns = @JoinColumn(name = "course_id"))
	@Column(name = "day_of_week")
	@Enumerated(EnumType.STRING)
	private Set<DayOfWeek> daysOfWeek = new HashSet<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseFrequencyOption> frequencyOptions = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ClassSession> classSessions = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Assessment> assessments = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CourseAssignment> courseAssignments = new ArrayList<>();

	@NotBlank(message = "Code cannot be empty")
	private String code;

	@NotBlank(message = "Name cannot be empty")
	private String name;

	private String description;

	@NotNull(message = "Status cannot be empty")
	@Enumerated(EnumType.STRING)
	private CourseStatus status;

	@NotNull(message = "Start date cannot be empty")
	private LocalDate startDate;

	private LocalDate endDate; // Can be null for indefinite courses

	@NotNull(message = "Start time cannot be empty")
	private LocalTime startTime;

	@NotNull(message = "End time cannot be empty")
	private LocalTime endTime;

	public void validate() {
		if (!CourseStatus.COURSE_STATUS.contains(this.status)) {
			throw new BadRequestException("Invalid course status: " + this.status);
		}
		if (frequencyOptions == null || frequencyOptions.isEmpty()) {
			throw new BadRequestException("Frequency options cannot be empty for the course.");
		}
		if (daysOfWeek == null || daysOfWeek.isEmpty()) {
			throw new BadRequestException("Course days of week cannot be empty.");
		}
		if (startDate == null || endDate == null || startTime == null) {
			return;
		}
		if (endDate != null && endDate.isBefore(startDate)) {
			throw new BadRequestException("Start Date must be before End Date.");
		}
		if (endTime.isBefore(startTime)) {
			throw new BadRequestException("Start Time must be before End Time.");
		}
	}

	public void addFrequencyOption(CourseFrequencyOption option) {
		if (frequencyOptions == null) {
			frequencyOptions = new ArrayList<>();
		}
		frequencyOptions.add(option);
		option.setCourse(this);
	}

	public void removeFrequencyOption(CourseFrequencyOption option) {
		if (frequencyOptions != null) {
			frequencyOptions.remove(option);
			option.setCourse(null);
		}
	}
}
