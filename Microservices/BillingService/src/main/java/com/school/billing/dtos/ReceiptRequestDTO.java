package com.school.billing.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.school.billing.constants.PaymentMethod;
import com.school.billing.entities.StudentAccount;

import lombok.Data;

@Data
public class ReceiptRequestDTO {
    private StudentAccount studentAccount;
    private BigDecimal amount;
    private LocalDate receiptDate;
    private String remark;
    private PaymentMethod paymentMethod;
}
