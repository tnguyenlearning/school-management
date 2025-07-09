package com.school.student.education.entity;

import java.time.LocalDate;

import com.school.constant.LeaveStatus;
import com.school.student.enrollment.entity.Student;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LEAVE_APPROVAL")
public class LeaveApproval {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "session_id")
	private ClassSession classSession;

	private LocalDate leaveDate;

	@Enumerated(EnumType.STRING)
	private LeaveStatus status; // REQUESTED, APPROVED, REJECTED

	private LocalDate approvalDate;

	private String approvalReason;

}
