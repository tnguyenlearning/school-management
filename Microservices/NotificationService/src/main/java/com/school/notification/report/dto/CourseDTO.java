package com.school.notification.report.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
	private String courseID;
	private String name;
	private BigDecimal estFee;
	private BigDecimal actualFee;
	private List<DiscountDTO> discounts;
	private List<RefundDTO> refunds;
	private JRBeanCollectionDataSource discountDataSource;
	private JRBeanCollectionDataSource refundDataSource;
}
