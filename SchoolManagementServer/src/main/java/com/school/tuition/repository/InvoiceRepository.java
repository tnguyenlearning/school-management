package com.school.tuition.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.constant.InvoiceStatus;
import com.school.tuition.entity.Invoice;

@RepositoryRestResource(path = "invoice")
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	List<Invoice> findAllByStatus(InvoiceStatus status);

	List<Invoice> findByDueDate(LocalDate dueDate);

	@Query("SELECT i FROM Invoice i WHERE i.dueDate = :targetDate AND i.status <> 'PAID'")
	List<Invoice> findPendingInvoicesByDueDate(@Param("targetDate") LocalDate targetDate);

}
