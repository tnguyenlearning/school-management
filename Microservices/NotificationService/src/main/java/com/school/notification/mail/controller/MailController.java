package com.school.notification.mail.controller;

import org.springframework.web.bind.annotation.RestController;

import com.school.notification.mail.dto.MailInfoDTO;
import com.school.notification.mail.service.MailService;
import com.school.notification.report.dto.InvoiceDTO;
import com.school.notification.report.service.ReportService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class MailController {
	@Autowired
	private MailService mailService;
	@Autowired
	private ReportService reportService;

	@PostMapping("/simple-mail")
	@ResponseStatus(HttpStatus.OK)
	public String postMethodName(@RequestBody MailInfoDTO mailInfo) {
		// TODO: process POST request
		mailService.sendSimpleMail(mailInfo);
		return "Mail sent";
	}

	@PostMapping("/invoice")
	@ResponseStatus(HttpStatus.OK)
	public String sendInvoice(@RequestBody InvoiceDTO invoice)
			throws JRException, MessagingException, FileNotFoundException {
		// TODO: process POST request
		mailService.sendInvoice(invoice);
		return "Mail sent";
	}
	
	@GetMapping("/print/invoice")
	@ResponseStatus(HttpStatus.OK)
	public void testInvoice(@RequestBody InvoiceDTO invoice, HttpServletResponse response)
			throws JRException, MessagingException, IOException {
		// TODO: process POST request
		reportService.printInvoice2(invoice, response);
	}
}
