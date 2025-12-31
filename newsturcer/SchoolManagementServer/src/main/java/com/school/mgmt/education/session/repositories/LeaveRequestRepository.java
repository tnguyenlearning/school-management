package com.school.mgmt.education.session.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.mgmt.education.session.entities.LeaveRequest;
import com.school.utilslibrary.constant.education.LeaveStatus;

@RepositoryRestResource(path = "leave-requests")
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

	@Query("""
			  SELECT lr FROM LeaveRequest lr
			  WHERE (:leaveDateFrom IS NULL OR lr.leaveDate >= :leaveDateFrom)
			    AND (:leaveDateTo   IS NULL OR lr.leaveDate   <= :leaveDateTo)
			    AND (:status        IS NULL OR lr.status      =  :status)
			    AND (:requestedFrom IS NULL OR lr.requestedDate >= :requestedFrom)
			    AND (:requestedTo   IS NULL OR lr.requestedDate <= :requestedTo)
			    AND (:approvedFrom  IS NULL OR lr.approvedDate  >= :approvedFrom)
			    AND (:approvedTo    IS NULL OR lr.approvedDate  <= :approvedTo)
			    AND (:rejectedFrom  IS NULL OR lr.rejectedDate  >= :rejectedFrom)
			    AND (:rejectedTo    IS NULL OR lr.rejectedDate  <= :rejectedTo)
			""")
	Page<LeaveRequest> findLeaveRequestsByFilters(
			@Param("leaveDateFrom") LocalDate leaveDateFrom,
			@Param("leaveDateTo") LocalDate leaveDateTo,

			@Param("status") LeaveStatus status, // enum or string—match your entity

			@Param("requestedFrom") LocalDate requestedFrom,
			@Param("requestedTo") LocalDate requestedTo,

			@Param("approvedFrom") LocalDate approvedFrom,
			@Param("approvedTo") LocalDate approvedTo,

			@Param("rejectedFrom") LocalDate rejectedFrom,
			@Param("rejectedTo") LocalDate rejectedTo,

			Pageable pageable);

}
