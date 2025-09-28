package com.school.billing.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.billing.entities.BillingHeader;

@RepositoryRestResource(path = "billing-headers")
public interface BillingHeaderRepository
		extends JpaRepository<BillingHeader, Long>, JpaSpecificationExecutor<BillingHeader> {

	@Query("SELECT b.enrollmentId FROM BillingHeader b "
			+ "WHERE b.studentCode BETWEEN :fromStudentCode AND :toStudentCode " + "AND :effDate >= b.nextBillingDate")
	List<Long> findEnrollmentIdsByStudentCodeRange(String fromStudentCode, String toStudentCode, LocalDate effDate);

	BillingHeader findByEnrollmentId(Long enrollmentId);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("""
			    UPDATE BillingHeader b
			    SET b.nextBillingDate = :nextBillingDate
			    WHERE b.enrollmentId IN :enrollmentIds
			      AND (b.nextBillingDate < :nextBillingDate)
			""")
	int updateNextBillingDateIfEarlier(@Param("enrollmentIds") List<Long> enrollmentIds,
			@Param("nextBillingDate") LocalDate nextBillingDate);

}
