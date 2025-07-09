package com.school.notification.report.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.school.notification.report.dto.CourseDTO;
import com.school.notification.report.dto.InvoiceDTO;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportService {

	public byte[] printInvoice(InvoiceDTO invoice) throws FileNotFoundException, JRException {
		// tìm kiếm file report
		File file = ResourceUtils.getFile("classpath:reports/Invoice.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		// compile file report cùng các tham số đã khai báo
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, initInvoiceParameters(invoice),
				new JREmptyDataSource());
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	public void printInvoice2(InvoiceDTO invoice, HttpServletResponse response) throws JRException, IOException {
		// tìm kiếm file report
		File file = ResourceUtils.getFile("classpath:reports/Invoice.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		// compile file report cùng các tham số đã khai báo
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, initInvoiceParameters(invoice),
				new JREmptyDataSource());
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.addHeader("Content-disposition", "attachment; filename=NameofPdf.pdf");
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}

	private Map<String, Object> initInvoiceParameters(InvoiceDTO invoice) {
		Map<String, Object> parameters = new HashMap<>();
		BigDecimal total = BigDecimal.valueOf(0);
		parameters.put("invoiceID", invoice.getInvoiceID());
		parameters.put("name", invoice.getName());
		parameters.put("address", invoice.getAddress());
		parameters.put("phone", invoice.getPhone());
		for (CourseDTO course : invoice.getCourses()) {
			Optional.ofNullable(course.getDiscounts()).orElseGet(Collections::emptyList).stream().map(discount -> {
				discount.setValue(discount.getValue().multiply(BigDecimal.valueOf(-1)));
				return discount;
			}).collect(Collectors.toList());
			Optional.ofNullable(course.getRefunds()).orElseGet(Collections::emptyList).stream().map(refund -> {
				refund.setValue(refund.getValue().multiply(BigDecimal.valueOf(-1)));
				return refund;
			}).collect(Collectors.toList());
			course.setDiscountDataSource(new JRBeanCollectionDataSource(course.getDiscounts()));
			course.setRefundDataSource(new JRBeanCollectionDataSource(course.getRefunds()));
			total = total.add(course.getActualFee() == null ? BigDecimal.valueOf(0) : course.getActualFee());
		}
		parameters.put("courses", new JRBeanCollectionDataSource(invoice.getCourses()));
		parameters.put("total", total);
		return parameters;
	}
}