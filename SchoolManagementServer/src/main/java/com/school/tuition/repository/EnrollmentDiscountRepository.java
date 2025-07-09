package com.school.tuition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.tuition.entity.EnrollmentDiscount;

@Repository
public interface EnrollmentDiscountRepository extends JpaRepository<EnrollmentDiscount, Long> {

}
