package com.school.billing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.billing.entities.Refund;

//@RepositoryRestResource(path = "refund")
public interface RefundRepository extends JpaRepository<Refund, Long> {

}
