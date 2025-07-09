package com.school.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**") // Allows all endpoints to be accessed
//				.allowedOrigins("http://localhost:3000") // Allow the frontend URL
//				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
//				.allowedHeaders("*") // Allow all headers
//				.allowCredentials(true); // Allow credentials (if needed)
//	}
}
