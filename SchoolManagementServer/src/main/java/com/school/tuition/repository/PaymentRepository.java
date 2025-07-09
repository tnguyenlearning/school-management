package com.school.tuition.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.tuition.entity.Invoice;
import com.school.tuition.entity.Payment;

@RepositoryRestResource(path = "payment")
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByInvoice(Invoice invoice);

//	@Query("SELECT paymentDate from payment")
//	BigDecimal sumPaymentsByInvoice(Long invoiceId);

}
