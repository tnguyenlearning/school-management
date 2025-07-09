package com.school.autonum.dto;

import com.school.constant.AnumType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmNumberRequest {
	
	private AnumType type;
	private String number;
	
}
