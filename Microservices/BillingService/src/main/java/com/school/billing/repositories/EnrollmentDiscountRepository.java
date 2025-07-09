package com.school.billing.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.billing.entities.EnrollmentDiscount;

@Repository
public interface EnrollmentDiscountRepository extends JpaRepository<EnrollmentDiscount, Long> {

	List<EnrollmentDiscount> findAllByEnrollmentId(Long enrollmentId);

}
