package com.school.exception;

public class CustomUniqueConstraintViolationException extends RuntimeException {

	public CustomUniqueConstraintViolationException(String message) {
		super(message);
	}
}
