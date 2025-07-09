package com.school.utils.annotations;

import java.lang.annotation.*;

import com.school.utils.annotations.validators.UniqueValueValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValueValidator.class) // Specify the validator
@Documented
public @interface UniqueValue {
	String message() default "This value must be unique";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	// Additional parameters for validation
	String fieldName();

	Class<?> entityClass();
}
