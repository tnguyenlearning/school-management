package com.school.billing.controllers;

import java.util.List;

import com.school.billing.entities.Payment;
import com.school.billing.entities.Receipt;
import com.school.billing.entities.StudentAccount;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentAccountDetailsDTO {

	private StudentAccount studentAccount;

	private List<Receipt> receipts;
	private List<Payment> payments;

}
