package com.school.notification.report.dto;

import java.util.List;

import com.school.notification.mail.dto.MailInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
	private List<CourseDTO> courses;
	private MailInfoDTO mailInfo;
	private String invoiceID;
	private String name;
	private String address;
	private String phone;
}
