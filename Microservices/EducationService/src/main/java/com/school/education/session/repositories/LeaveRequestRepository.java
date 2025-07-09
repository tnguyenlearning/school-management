package com.school.education.session.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.school.education.session.entities.LeaveRequest;

@RepositoryRestResource(path = "leave-request")

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

}
