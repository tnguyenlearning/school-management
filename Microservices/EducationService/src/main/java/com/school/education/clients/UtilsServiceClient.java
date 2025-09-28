package com.school.education.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.school.education.clients.configs.FeignClientConfig;
import com.school.utilslibrary.clients.utils.ConfirmNumberRequest;

@FeignClient(name = "utils-service", path = "/api/utils", configuration = FeignClientConfig.class)
public interface UtilsServiceClient {

	@PostMapping("/v2/confirm")
	void confirmNumber(ConfirmNumberRequest request);

}
