package com.school.tuition.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.school.tuition.entity.BillingCycle;

@RepositoryRestResource(path = "billing-cycle")

public interface BillingCycleRepository
		extends JpaRepository<BillingCycle, Long>, JpaSpecificationExecutor<BillingCycle> {
	@Query("SELECT b FROM BillingCycle b " + "WHERE b.student.id BETWEEN :fromStudentId AND :toStudentId "
			+ "AND b.nextCycleStartDate <= :effDate")
	List<BillingCycle> findBillingCyclesForStudentRangeWithDueDate(@Param("fromStudentId") Long fromStudentId,
			@Param("toStudentId") Long toStudentId, @Param("effDate") LocalDate effDate);

	@Query("SELECT b FROM BillingCycle b WHERE b.enrollment.id = :enrollmentId ORDER BY b.nextCycleStartDate DESC")
	BillingCycle findLastBillingByEnrollmentId(@Param("enrollmentId") Long enrollmentId);

	@Query("SELECT bc FROM BillingCycle bc WHERE bc.enrollment.id = :enrollmentId")
	BillingCycle findActiveBillingCycleByEnrollmentId(@Param("enrollmentId") Long enrollmentId);

	Optional<BillingCycle> findTopByEnrollmentIdOrderByCycleNumDesc(Long enrollmentId);

}
