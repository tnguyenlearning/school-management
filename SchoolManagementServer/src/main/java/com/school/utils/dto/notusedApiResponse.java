package com.school.utils.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class notusedApiResponse<T> {

	private boolean success;
	private String message;
	private T data;
	private int statusCode;

	// Default constructor
	public notusedApiResponse() {
	}

	// Constructor for successful response
	public notusedApiResponse(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
		this.statusCode = success ? 200 : 500;
	}

	// Constructor for error response
	public notusedApiResponse(boolean success, String message, int statusCode) {
		this.success = success;
		this.message = message;
		this.statusCode = statusCode;
		this.data = null;
	}

	// Utility methods for common responses
	public static <T> notusedApiResponse<T> success(String message, T data) {
		return new notusedApiResponse<>(true, message, data);
	}

	public static <T> notusedApiResponse<T> error(String message, int statusCode) {
		return new notusedApiResponse<>(false, message, statusCode);
	}

}
