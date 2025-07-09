package com.school.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.user.entities.Teacher;

@RepositoryRestResource(path = "teachers")
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
