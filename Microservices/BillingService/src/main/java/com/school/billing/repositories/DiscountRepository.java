package com.school.billing.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.billing.entities.Discount;

@RepositoryRestResource(path = "discounts")
public interface DiscountRepository extends JpaRepository<Discount, Long> {
	
	@Query("select d from Discount d")
	List<Discount> findActiveDiscountsByEnrollmentId(String enrollmentId);

}
