package com.school.billing.clients.configs;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignClientConfig {

	@Bean
	ErrorDecoder errorDecoder() {
		return new FeignCustomErrorDecoder();
	}

	@Bean
	Retryer retryer() {
		return new Retryer.Default(100, TimeUnit.MILLISECONDS.toMillis(1), 3);
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

}
