package com.school.auth.dto;

import java.util.List;

import com.school.auth.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {
	
	private String username;
	private List<Role> roles;
	
}
