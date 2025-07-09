package com.school.student.enrollment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.school.student.enrollment.entity.Student;

@RepositoryRestResource(path = "students")

public interface StudentRepository extends JpaRepository<Student, Long> {

	@RestResource(path = "findByPhone")
	public Page<Student> findByPhoneNumberContaining(String phone, Pageable p);

	@RestResource(path = "findByStudentCode")
	public Page<Student> findByStudentCodeContaining(String studentCode, Pageable p);

	@RestResource(path = "findByFirstName")
	public Page<Student> findByFirstNameContaining(String firstName, Pageable p);

	@Query("SELECT COUNT(s) FROM Student s WHERE s.email = :email AND s.id != :id")
	public int checkemailUniqueForUpdate(Long id, String email);

}
