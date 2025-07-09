package com.school.student.enrollment.repository;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import com.school.auth.service.UserService;
import com.school.exception.BadRequestException;
import com.school.student.education.service.CourseService;
import com.school.student.education.service.EnrollmentService;
import com.school.student.enrollment.entity.Student;
import com.school.student.enrollment.service.StudentService;

import lombok.RequiredArgsConstructor;

@RepositoryEventHandler(Student.class)
@RequiredArgsConstructor
public class StudentEventHandler {
	 private final StudentRepository studentRepository;
	 
//	 @HandleBeforeSave
//	    public void validateUniqueFields(Student student) {
//	        if (studentRepository.checkemailUniqueForUpdate(student.getId(), student.getEmail()) != 0)  {
//	            throw new BadRequestException("Email must be unique");
//	        }
//	    }
//	 
//	 @HandleBeforeCreate
//	    public void validateUniqueFields(Student student) {
//	        if (studentRepository.checkemailUniqueForUpdate(student.getId(), student.getEmail()) != 0)  {
//	            throw new BadRequestException("Email must be unique");
//	        }
//	    }

}
