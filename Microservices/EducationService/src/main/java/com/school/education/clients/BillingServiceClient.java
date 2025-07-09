package com.school.education.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.school.education.clients.configs.FeignClientConfig;
import com.school.education.clients.dto.FrequencyOptionResponseDTO;
import com.school.education.clients.dtos.CourseAllowedFrequencyRequest;
import com.school.education.clients.dtos.CourseAllowedFrequencyResponseWrapperDTO;
import com.school.utilslibrary.clients.billing.dtos.AllowedFrequencyRequest;
import com.school.utilslibrary.clients.billing.dtos.BillingHeaderDTO;

@FeignClient(name = "billing-service", path = "/api/billing", configuration = FeignClientConfig.class)
public interface BillingServiceClient {

	@GetMapping("/v0/billing-frequency")
	FrequencyOptionResponseDTO getAllFrequencies();

	@PostMapping("/v0/course-allowed-frequency")
	void createCourseAllowedFrequency(CourseAllowedFrequencyRequest request);

	@PutMapping("/v2/course-allowed-frequency/courses/{courseId}")
	void updateCourseAllowedFrequency(@PathVariable Long courseId, List<AllowedFrequencyRequest> request);

	@GetMapping("/v0/course-allowed-frequency/search/findByCourseId")
	CourseAllowedFrequencyResponseWrapperDTO findByCourseId(Long courseId);

	@PostMapping("/v0/billing-header")
	void createBillingHeader(BillingHeaderDTO request);

}
