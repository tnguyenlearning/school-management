package com.school.exception.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
	
    @JsonProperty("error_code")
    private HttpStatus errorCode;

    @JsonProperty("error_message")
    private String errorMessage;
    
}
