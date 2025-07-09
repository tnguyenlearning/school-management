package com.school.notification.mail.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailInfoDTO {
	private String username;
	private String from;

	@Email
	private String to;

	private String subject;
	private String content;
}
