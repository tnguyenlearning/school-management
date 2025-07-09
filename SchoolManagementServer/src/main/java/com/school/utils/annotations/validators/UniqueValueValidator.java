package com.school.utils.annotations.validators;

import org.springframework.stereotype.Component;

import com.school.config.ApplicationContextProvider;
import com.school.utils.annotations.UniqueValue;
import com.school.utils.annotations.UniqueValueService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, String> {
	private String fieldName;
	private Class<?> entityClass;

	@Override
	public void initialize(UniqueValue constraintAnnotation) {
		this.fieldName = constraintAnnotation.fieldName();
		this.entityClass = constraintAnnotation.entityClass();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isEmpty()) {
			return true; // Skip validation for null or empty values
		}
		return true;
//		// Use ApplicationContextProvider to get the UniqueValueService bean
//		UniqueValueService uniqueValueService = ApplicationContextProvider.getApplicationContext()
//				.getBean(UniqueValueService.class);
//
//		// Check if the value is unique
//		return uniqueValueService.isValueUnique(value, fieldName, entityClass);
	}
}
