package com.school.student.education.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.student.education.entity.Attendance;
import com.school.student.education.entity.ClassSession;
import com.school.student.enrollment.entity.Student;

@RepositoryRestResource(path = "attendances")
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	Optional<Attendance> findByClassSessionAndStudent(ClassSession session, Student student);

}
