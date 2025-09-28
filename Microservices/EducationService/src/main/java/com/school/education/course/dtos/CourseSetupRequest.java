package com.school.education.course.dtos;

import java.util.List;

import com.school.education.course.entities.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSetupRequest {

	private CourseRequest course;
	private List<Long> frequencyOptionIds;

}
