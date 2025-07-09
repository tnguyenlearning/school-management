package com.school.auth.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.school.auth.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("refresh_token")
	private String refreshToken;
	private String username;
	private List<Role> roles;

}
