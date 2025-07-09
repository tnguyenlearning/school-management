package com.school.billing.constants;

public enum InvoiceStatus {
	PAID, PARTIALLY_PAID, UNPAID;

	public static boolean isValidTransition(InvoiceStatus current, InvoiceStatus next) {
		if (current == PAID)
			return false; // Cannot transition out of PAID
		if (current == UNPAID && next == PARTIALLY_PAID)
			return true;
		return current == PARTIALLY_PAID && next == PAID;
	}
}
