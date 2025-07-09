package com.school.billing.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.school.billing.clients.configs.FeignClientConfig;
import com.school.utilslibrary.clients.billing.dtos.DataForBillingDTO;
import com.school.utilslibrary.clients.billing.dtos.DataForBillingRequest;
import com.school.utilslibrary.restapi.ApiResponse;

@FeignClient(name = "education-service", path = "/api/education", configuration = FeignClientConfig.class)
public interface EducationServiceClient {

    @PostMapping("/v2/enrollments/getDataForBilling")
    ApiResponse<List<DataForBillingDTO>> getDataForBilling(@RequestBody DataForBillingRequest request);

    @GetMapping("/v2/students/search/findStudentCodeContaining")
    ApiResponse<List<String>> findStudentCodeContaining(
        @RequestParam(required = false) String phone,
        @RequestParam(required = false) String studentCode,
        @RequestParam(required = false) String firstName
    );
    
    @GetMapping("/v0/students/search/existsByStudentCode")
    Boolean existsByStudentCode(
        @RequestParam(required = false) String studentCode
    );
    
}
