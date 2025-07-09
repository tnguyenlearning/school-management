package com.school.education.session.entities;

import java.time.LocalDate;

import com.school.education.constants.LeaveStatus;

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
	@Column(nullable = false)
	private String studentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "session_id", nullable = false)
	private ClassSession classSession;

	@Column(nullable = false)
	private LocalDate leaveDate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private LeaveStatus status; // REQUESTED, APPROVED, REJECTED

	private LocalDate requestedDate;
	private LocalDate approvedDate;
	private LocalDate rejectedDate;

	@Column(nullable = false)
	private String reason;

}
