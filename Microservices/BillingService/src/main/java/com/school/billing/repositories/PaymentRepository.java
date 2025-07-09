package com.school.billing.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.billing.entities.Invoice;
import com.school.billing.entities.Payment;

@RepositoryRestResource(path = "payments")
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	//List<Payment> findByStudentAccountId(Long studentAccountId);

	List<Payment> findByInvoice(Invoice invoice);

	Payment save(Payment payment);

	@Query("SELECT p FROM Payment p WHERE " +
	       "(:studentAccountId IS NULL OR p.studentAccount.id = :studentAccountId) AND " +
	       "(:startDate IS NULL OR p.paymentDate >= :startDate) AND " +
	       "(:endDate IS NULL OR p.paymentDate <= :endDate)")
	Page<List<Payment>> searchPayments(
	    @Param("studentAccountId") Long studentAccountId,
	    @Param("startDate") LocalDate startDate,
	    @Param("endDate") LocalDate endDate,
	    Pageable pageable);

	//	@Query("SELECT paymentDate from payment")
	//	BigDecimal sumPaymentsByInvoice(String invoiceId);

}
