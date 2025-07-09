package com.school.education.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.school.education.clients.dtos.CourseAllowedFrequencyRequest;

@FeignClient(name = "course-allowed-frequency", path = "/api/billing/course-allowed-frequency")
public interface CourseAllowedFrequencyClient {

   
	
}

