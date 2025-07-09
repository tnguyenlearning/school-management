package com.school.education.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.education.course.entities.CourseAssignment;

@RepositoryRestResource(path = "course-assignments")
public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, Long> {
}
