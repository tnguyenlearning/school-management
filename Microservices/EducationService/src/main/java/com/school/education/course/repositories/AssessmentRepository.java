package com.school.education.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.education.course.entities.Assessment;

@RepositoryRestResource(path = "assessments")
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}
