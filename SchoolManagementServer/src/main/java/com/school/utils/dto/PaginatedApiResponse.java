package com.school.utils.dto;

import java.util.List;

public class PaginatedApiResponse<T> extends notusedApiResponse<List<T>> {

	private int totalPages;
	private long totalElements;

	public PaginatedApiResponse(boolean success, String message, List<T> data, int totalPages, long totalElements) {
		super(success, message, data);
		this.totalPages = totalPages;
		this.totalElements = totalElements;
	}

	// Getters and Setters
	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
}
