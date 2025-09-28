package com.school.education.session.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

import com.school.education.course.entities.Course;
import com.school.education.session.entities.ClassSession;
import com.school.education.constants.SessionStatus;

@RepositoryRestResource(path = "class-session")
public interface ClassSessionRepository extends JpaRepository<ClassSession, Long> {

	Optional<ClassSession> findByCourseAndSessionDate(Course course, LocalDate sessionDate);

	// Find the latest session for a course by session date
	Optional<ClassSession> findTopByCourseOrderBySessionDateDesc(Course course);

	@Query("""
		    SELECT cs FROM ClassSession cs
		    WHERE cs.course.id = :courseId
		      AND (:sessionDateFrom IS NULL OR cs.sessionDate >= :sessionDateFrom)
		      AND (:sessionDateTo IS NULL OR cs.sessionDate <= :sessionDateTo)
		      AND (:status IS NULL OR cs.status = :status)
		""")
		Page<ClassSession> searchClassSessionsByCourseId(
		        @Param("courseId") Long courseId,
		        @Param("sessionDateFrom") LocalDate sessionDateFrom,
		        @Param("sessionDateTo") LocalDate sessionDateTo,
		        @Param("status") SessionStatus status,
		        Pageable pageable);

	
	@Query("SELECT cs FROM ClassSession cs WHERE " +
			"cs.course.code = :courseCode " +
			"AND (:sessionDate IS NULL OR cs.sessionDate = :sessionDate) " +
			"AND (:status IS NULL OR cs.status = :status)")
	Page<ClassSession> searchClassSessions(
			@Param("courseCode") String courseCode,
			@Param("sessionDate") LocalDate sessionDate,
			@Param("status") SessionStatus status,
			Pageable pageable);

}
