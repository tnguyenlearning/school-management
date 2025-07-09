package com.school.tuition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.tuition.entity.CourseFrequencyOption;

@RepositoryRestResource(path = "course-frequency-options")
public interface CourseFrequencyOptionRepository extends JpaRepository<CourseFrequencyOption, Long> {

}
