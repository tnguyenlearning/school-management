package com.school.education.enrollment.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.school.education.course.entities.Course;
import com.school.education.enrollment.dto.StudentEnrollmentDTO;
import com.school.education.enrollment.entities.Enrollment;
import com.school.education.enrollment.entities.Student;
import com.school.utilslibrary.clients.billing.constants.EnrollmentStatus;

import jakarta.transaction.Transactional;

@RepositoryRestResource(path = "enrollments")
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

	@Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId")
	List<Enrollment> findAllByCourseId(Long courseId);

	@Query("SELECT e.id FROM Enrollment e WHERE e.course.id = :courseId")
	List<Long> findIdsByCourseId(Long courseId);
	
	@Query("SELECT e FROM Enrollment e WHERE e.id IN :enrollmentIds")
	List<Enrollment> findAllByEnrollmentIds(@Param("enrollmentIds") List<Long> enrollmentIds);

	@Query("SELECT new com.school.education.enrollment.dto.StudentEnrollmentDTO("
			+ "s.id, s.studentCode, s.firstName, s.lastName, s.gender, s.age, s.phoneNumber, s.email, s.address, "
			+ "e.id, e.enrollmentDate, e.startDate, e.endDate, e.status) "
			+ "FROM Student s " + "LEFT JOIN Enrollment e ON s.id = e.student.id "
			+ "WHERE e.course.id = :courseId")
	List<StudentEnrollmentDTO> findStudentsAndEnrollmentByCourseId(@Param("courseId") Long courseId);
	
	@Query("SELECT s FROM Student s WHERE s.studentCode LIKE %:studentCode% "
			+ "AND s.id NOT IN (SELECT e.student.id FROM Enrollment e WHERE e.course.id = :courseId)")
	List<Student> findStudentsByStudentCodeNotEnrolled(Long courseId, String studentCode);

	@Query("SELECT s FROM Student s WHERE s.phoneNumber LIKE %:phoneNumber% "
			+ "AND s.id NOT IN (SELECT e.student.id FROM Enrollment e WHERE e.course.id = :courseId)")
	List<Student> findStudentsByPhoneNotEnrolled(Long courseId, String phoneNumber);

	@Query("SELECT s FROM Student s WHERE s.firstName LIKE %:firstName% "
			+ "AND s.id NOT IN (SELECT e.student.id FROM Enrollment e WHERE e.course.id = :courseId)")
	List<Student> findStudentsByFirstNameNotEnrolled(Long courseId, String firstName);

	//
//	@Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId")
//	List<Student> findStudentsByCourseId(String courseId);
//
//	@Query("SELECT e.course FROM Enrollment e WHERE e.student.id = :studentId")
//	List<Course> findCoursesByStudentId(String studentId);
//
//	@Modifying
//	@Transactional
//	@Query("DELETE FROM Enrollment e WHERE e.course.id = :courseId AND e.student.id = :studentId")
//	int deleteByCourseIdAndStudentId(String courseId, String studentId);
//
//	Optional<Enrollment> findByCourseIdAndStudentId(String courseId, String studentId);
//
//	@Query("SELECT e FROM Enrollment e "
//			+ "WHERE e.validflag=1 AND e.student.id BETWEEN :fromStudentId AND :toStudentId "
//			+ "AND e.status = :status " + "AND :effDate >= e.nextGenerateBillingDate")
//	List<Enrollment> findByStudentIdRangeDueToGenerateBilling(@Param("fromStudentId") String fromStudentId,
//			@Param("toStudentId") String toStudentId, @Param("status") EnrollmentStatus status,
//			@Param("effDate") LocalDate effDate);

}
