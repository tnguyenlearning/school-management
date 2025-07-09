package com.school.billing.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceService {

//	private final InvoiceRepository invoiceRepository;
//	private BillingCycleService billingCycleService;
//
//	@Autowired
//	public void setBillingCycleService(@Lazy BillingCycleService billingCycleService) {
//		this.billingCycleService = billingCycleService;
//	}
//
//	public Invoice findById(String invoiceId) {
//		return invoiceRepository.findById(invoiceId).orElseThrow(() -> new RuntimeException("Invoice not found"));
//	}
//
//	public void save(Invoice invoice) {
//		invoiceRepository.save(invoice);
//	}
//
//	public void updateInvoiceStatus(String invoiceId) {
//		Invoice invoice = invoiceRepository.findById(invoiceId)
//				.orElseThrow(() -> new NotFoundException("Invoice not found"));
//
//		BigDecimal totalPayments = BigDecimal.ZERO;// paymentRepository.sumPaymentsByInvoice(invoiceId);
//
//		if (totalPayments.compareTo(invoice.getAmountDue()) >= 0) {
//			invoice.setStatus(InvoiceStatus.PAID);
//		} else if (totalPayments.compareTo(BigDecimal.ZERO) > 0) {
//			invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
//		} else {
//			invoice.setStatus(InvoiceStatus.UNPAID);
//		}
//
//		invoiceRepository.save(invoice);
//	}
//
//	@Transactional
//	public List<Invoice> generateInvoicesForBilling(String fromStudentId, String toStudentId, LocalDate dueBillingDate) {
//		List<BillingCycle> billingCycles = billingCycleService.getBillingsForInvoiceGeneration(fromStudentId,
//				toStudentId, dueBillingDate);
//
//		if (billingCycles.isEmpty()) {
//			return null;
//		}
//
//		List<Invoice> invoices = new ArrayList<>();
//		for (BillingCycle billingCycle : billingCycles) {
//			Invoice invoice = createInvoiceFrombilling(billingCycle);
//			invoices.add(invoice);
//		}
//
//		return invoices;
//	}
//
//	public Invoice createInvoiceFrombilling(BillingCycle billingCycle) {
//		Invoice invoice = new Invoice();
//		invoice.setBillingCycle(billingCycle);
//		invoice.setCourseId(billingCycle.getCourseId());
//		invoice.setStudentId(billingCycle.getStudentId());
//
//		invoice.setAmountPaid(BigDecimal.ZERO);
//
//		invoice.setIssueDate(billingCycle.getGeneratedDate());
//		invoice.setDueDate(billingCycle.getCycleStartDate());
//		invoice.setPaymentType(PaymentType.COMPLETE);
//		invoice.setStatus(InvoiceStatus.UNPAID);
//		invoice.setType(Invoice.getInvoiceTypeByFrequency(billingCycle.getFrequency()));
//		invoice.setDescription(null);
//		return invoiceRepository.save(invoice);
//	}

}
