package com.school.student.enrollment.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.school.constant.EnrollmentStatus;
import com.school.student.education.entity.Course;
import com.school.student.enrollment.dto.StudentEnrollmentDTO;
import com.school.student.enrollment.entity.Enrollment;
import com.school.student.enrollment.entity.Student;

import jakarta.transaction.Transactional;

@RepositoryRestResource(path = "enrollments")

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

	@Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId AND e.validflag = 1")
	List<Enrollment> findAllByCourseId(Long courseId);

	@Query("SELECT new com.school.student.enrollment.dto.StudentEnrollmentDTO("
			+ "s.id, s.studentCode, s.firstName, s.lastName, s.gender, s.age, s.phoneNumber, s.email, s.address, "
			+ "e.id, e.enrollmentDate, e.startDate, e.endDate, e.status, "
			+ "new com.school.student.education.dto.CourseFrequencyOptionDTO(cfo.id, cfo.frequency, cfo.totalLearningDays, cfo.feeAmount)) "
			+ "FROM Student s " + "LEFT JOIN Enrollment e ON s.id = e.student.id "
			+ "LEFT JOIN CourseFrequencyOption cfo ON e.frequencyOption.id = cfo.id "
			+ "WHERE e.course.id = :courseId AND e.validflag = 1")
	List<StudentEnrollmentDTO> findStudentsAndEnrollmentByCourseId(Long courseId);
	
	@Query("SELECT new com.school.student.enrollment.dto.StudentEnrollmentDTO("
			+ "s.id, s.studentCode, s.firstName, s.lastName, s.gender, s.age, s.phoneNumber, s.email, s.address, "
			+ "e.id, e.enrollmentDate, e.startDate, e.endDate, e.status, "
			+ "new com.school.student.education.dto.CourseFrequencyOptionDTO(cfo.id, cfo.frequency, cfo.totalLearningDays, cfo.feeAmount)) "
			+ "FROM Student s " + "LEFT JOIN Enrollment e ON s.id = e.student.id "
			+ "LEFT JOIN CourseFrequencyOption cfo ON e.frequencyOption.id = cfo.id "
			+ "WHERE e.id = :enrollmentId AND e.validflag = 1")
	StudentEnrollmentDTO findStudentAndEnrollmentByEnrollmentId(Long enrollmentId);

	@Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId")
	List<Student> findStudentsByCourseId(Long courseId);

	@Query("SELECT e.course FROM Enrollment e WHERE e.student.id = :studentId")
	List<Course> findCoursesByStudentId(Long studentId);

	@Modifying
	@Transactional
	@Query("DELETE FROM Enrollment e WHERE e.course.id = :courseId AND e.student.id = :studentId")
	int deleteByCourseIdAndStudentId(Long courseId, Long studentId);

	Optional<Enrollment> findByCourseIdAndStudentId(Long courseId, Long studentId);

	@Query("SELECT e FROM Enrollment e "
			+ "WHERE e.validflag=1 AND e.student.id BETWEEN :fromStudentId AND :toStudentId "
			+ "AND e.status = :status " + "AND :effDate >= e.nextGenerateBillingDate")
	List<Enrollment> findByStudentIdRangeDueToGenerateBilling(@Param("fromStudentId") Long fromStudentId,
			@Param("toStudentId") Long toStudentId, @Param("status") EnrollmentStatus status,
			@Param("effDate") LocalDate effDate);

}
