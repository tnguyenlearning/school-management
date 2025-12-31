package com.school.mgmt.billing.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.mgmt.billing.entity.BillingCycle;
import com.school.mgmt.billing.entity.Invoice;
import com.school.utilslibrary.constant.billing.InvoiceStatus;
import com.school.utilslibrary.constant.billing.InvoiceType;

@RepositoryRestResource(path = "invoices")
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	List<Invoice> findAllByStatus(InvoiceStatus status);

	List<Invoice> findByDueDate(LocalDate dueDate);

	@Query("SELECT i FROM Invoice i WHERE i.dueDate = :targetDate AND i.status <> 'PAID'")
	List<Invoice> findPendingInvoicesByDueDate(@Param("targetDate") LocalDate targetDate);

	@Query("SELECT i FROM Invoice i WHERE "
			+ "(:studentCode IS NULL OR i.studentCode = :studentCode) AND "
			+ "(:courseCode IS NULL OR i.courseCode = :courseCode) AND "
			+ "(:billingCycleId IS NULL OR i.billingCycle.id = :billingCycleId) AND "
			+ "(:issueDate IS NULL OR i.issueDate = :issueDate) AND "
			+ "(:publicDate IS NULL OR i.publicDate = :publicDate) AND "
			+ "(:status IS NULL OR i.status = :status) AND "
			+ "(:type IS NULL OR i.type = :type)")
	Page<List<Invoice>> searchInvoices(
			@Param("studentCode") String studentCode,
			@Param("courseCode") String courseCode,
			@Param("billingCycleId") Long billingCycleId,
			@Param("issueDate") LocalDate issueDate,
			@Param("publicDate") LocalDate publicDate,
			@Param("status") InvoiceStatus status,
			@Param("type") InvoiceType type, 
			Pageable pageable);
	
	@Query("SELECT i FROM Invoice i WHERE "
			+ "(:studentCode IS NULL OR i.studentCode = :studentCode) AND "
			+ "(:courseCode IS NULL OR i.courseCode = :courseCode) AND "
			+ "(:billingCycleId IS NULL OR i.billingCycle.id = :billingCycleId) AND "
			+ "(:issueDate IS NULL OR i.issueDate = :issueDate) AND "
			+ "(:publicDate IS NULL OR i.publicDate = :publicDate) AND "
			+ "(:status IS NULL OR i.status = :status) AND "
			+ "(:type IS NULL OR i.type = :type)")
	List<Invoice> searchInvoicesNoPage(
			@Param("studentCode") String studentCode,
			@Param("courseCode") String courseCode,
			@Param("billingCycleId") Long billingCycleId,
			@Param("issueDate") LocalDate issueDate,
			@Param("publicDate") LocalDate publicDate,
			@Param("status") InvoiceStatus status,
			@Param("type") InvoiceType type);

	List<Invoice> findAllByBillingCycleAndStatusIn(BillingCycle billingCycle, List<InvoiceStatus> statuses);

	List<Invoice> findAllByBillingCycle(BillingCycle billingCycle);

}
