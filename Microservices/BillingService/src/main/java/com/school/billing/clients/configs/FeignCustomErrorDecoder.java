package com.school.billing.clients.configs;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.school.utilslibrary.exception.BadRequestException;
import com.school.utilslibrary.exception.InternalException;
import com.school.utilslibrary.exception.NotFoundException;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FeignCustomErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		log.error("Feign Error - Method: {}, Status: {}, URL: {}", methodKey, response.status(),
				response.request().url());

		try {
			String responseBody = response.body() != null ? Util.toString(response.body().asReader())
					: "No Response Body";
			log.error("Feign Response Body: {}", responseBody);

			switch (response.status()) {
			case 400:
				return new BadRequestException(responseBody);
			case 404:
				return new NotFoundException("Resource not found: " + response.request().url());
			case 500:
				if (responseBody.contains("RetryableException")) {
					return new Exception("Service is temporarily unavailable: " + response.request().url());
				}
				return new InternalException("Server error: " + responseBody);
			default:
				return new Exception("Unexpected error: " + responseBody);
			}
		} catch (IOException e) {
			log.error("Error reading Feign response body", e);
			return new Exception("Unknown error occurred");
		}
	}

}
