package com.school.education.session.entities;

import java.time.LocalDate;

import com.school.education.constants.LeaveStatus;
import com.school.education.enrollment.entities.Student;
import com.school.utilslibrary.constants.RefundStatus;
import com.school.utilslibrary.constants.RefundType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "leave_request")
public class LeaveRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "StudentID cannot be empty")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "session_id", nullable = false)
	private ClassSession classSession;

	@Column(nullable = false)
	private LocalDate leaveDate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private LeaveStatus status; // REQUESTED, APPROVED, REJECTED

	@Enumerated(EnumType.STRING)
	private RefundType refundType; // MONEY, CREDIT_SESSION, NONE

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RefundStatus refundStatus; // NOT_ELIGIBLE, ELIGIBLE, REFUNDED
	
	@Column(nullable = false)
	private LocalDate requestedDate;
	
	private LocalDate approvedDate;
	private LocalDate rejectedDate;

	private String reason;
	
	private String remarks;

}
