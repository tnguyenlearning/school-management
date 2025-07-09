package com.school.tuition.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.tuition.entity.Payment;
import com.school.tuition.entity.Refund;

@RepositoryRestResource(path = "refund")
public interface RefundRepository extends JpaRepository<Refund, Long> {

	List<Refund> findByPayment(Payment payment);

}
