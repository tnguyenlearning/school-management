package com.school.billing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.billing.entities.BillingFrequencyOption;

@RepositoryRestResource(path = "billing-frequency")
public interface BillingFrequencyOptionRepository extends JpaRepository<BillingFrequencyOption, Long> {
}