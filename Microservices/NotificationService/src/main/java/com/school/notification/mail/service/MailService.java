package com.school.notification.mail.service;

import java.io.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.school.notification.mail.dto.MailInfoDTO;
import com.school.notification.report.dto.InvoiceDTO;
import com.school.notification.report.service.ReportService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;

@Service
public class MailService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private ReportService reportService;

	public void sendSimpleMail(@Valid @RequestBody MailInfoDTO mailInfo) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(initFrom(mailInfo.getFrom(), mailInfo.getUsername()));
		message.setTo(mailInfo.getTo());
		message.setSubject(mailInfo.getSubject());
		message.setText(mailInfo.getContent());
		mailSender.send(message);
	}

	public void sendInvoice(@RequestBody InvoiceDTO invoice)
			throws MessagingException, JRException, FileNotFoundException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(initFrom(invoice.getMailInfo().getFrom(), invoice.getMailInfo().getUsername()));
		helper.setTo(invoice.getMailInfo().getTo());
		helper.setSubject(invoice.getMailInfo().getSubject());
		helper.setText(invoice.getMailInfo().getUsername());
		helper.addAttachment("invoice.pdf", new ByteArrayResource(reportService.printInvoice(invoice)));
		mailSender.send(mimeMessage);

	}

	private String initFrom(String from, String username) {
		return new StringBuilder("\"").append(username).append("\" <").append(from).append(">").toString();
	}
}
