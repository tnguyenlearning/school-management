package com.school.education.session.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.school.education.course.entities.Course;
import com.school.education.session.entities.ClassSession;

@RepositoryRestResource(path = "class-session")

public interface ClassSessionRepository extends JpaRepository<ClassSession, Long> {

	Optional<ClassSession> findByCourseAndSessionDate(Course course, LocalDate sessionDate);

}
