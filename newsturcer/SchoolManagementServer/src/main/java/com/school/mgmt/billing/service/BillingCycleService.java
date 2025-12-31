package com.school.mgmt.billing.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.school.mgmt.billing.dto.CollectionRequestDTO;
import com.school.mgmt.billing.dto.CollectionResponseDTO;
import com.school.mgmt.billing.dto.GenerateBillingRequestDTO;
import com.school.mgmt.billing.dto.GenerateBillingResponseDTO;
import com.school.mgmt.billing.entity.BillingCycle;
import com.school.mgmt.billing.entity.BillingFrequencyOption;
import com.school.mgmt.billing.entity.Invoice;
import com.school.mgmt.billing.entity.Payment;
import com.school.mgmt.billing.entity.StudentAccount;
import com.school.mgmt.billing.repository.BillingCycleRepository;
import com.school.mgmt.billing.repository.InvoiceRepository;
import com.school.mgmt.billing.repository.PaymentRepository;
import com.school.mgmt.education.course.entity.Course;
import com.school.mgmt.education.enrollment.entities.Enrollment;
import com.school.mgmt.education.enrollment.entities.Student;
import com.school.mgmt.education.enrollment.repositories.EnrollmentRepository;
import com.school.utilslibrary.clients.education.constants.CourseStatus;
import com.school.utilslibrary.constant.GlobalConstants;
import com.school.utilslibrary.constant.billing.BillingCycleStatus;
import com.school.utilslibrary.constant.billing.Frequency;
import com.school.utilslibrary.constant.billing.InvoiceStatus;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class BillingCycleService {

	private final BillingCycleRepository billingCycleRepository;
	private final InvoiceRepository invoiceRepository;
	private final DiscountService discountService;
	private final PaymentRepository paymentRepository;
	private final StudentAccountService studentAccountService;
	private final EnrollmentRepository enrollmentRepository;


	
	private LocalDate effDate = null;
	private String fromStudentCode = null;
	private String toStudentCode = null;

	@Transactional
	public List<?> generateBillingForRangeStudents(GenerateBillingRequestDTO request) {
		effDate = request.getEffDate() != null ? request.getEffDate() : LocalDate.now();
		fromStudentCode = request.getFromStudentCode() != "" ? request.getFromStudentCode() : "st00000";
		toStudentCode = request.getToStudentCode() != "" ? request.getFromStudentCode() : "st99999";

		List<GenerateBillingResponseDTO> billingResponse = new ArrayList<>();
		GenerateBillingResponseDTO dto = null;
		// Get all active enrollments for the student
		List<Enrollment> enrollments = enrollmentRepository.getFilteredForBilling(fromStudentCode,
				toStudentCode, effDate);


		for (Enrollment enrollment : enrollments) {
			Course course = enrollment.getCourse();
			Student student = enrollment.getStudent();
			if (!course.getStatus().equals(CourseStatus.ONGOING)) {
				continue;
			}
			Set<DayOfWeek> learningDays = course.getDaysOfWeek();
			LocalDate endDate = enrollment.getEndDate();
			BillingFrequencyOption frequencyOption = enrollment.getBillingFrequencyOption();
			Integer totalLearningDays = frequencyOption.getTotalLearningDays();
			BigDecimal feeAmount = frequencyOption.getFeeAmount();
			Frequency frequency = frequencyOption.getFrequency();

			BigDecimal totalDiscount = discountService.calculateTotalDiscountAmount(feeAmount, effDate, enrollment.getId(), course.getCode());

			BillingCycle latestCycle = billingCycleRepository
					.findTopByEnrollmentIdOrderByCycleNumDesc(enrollment.getId()).orElse(null);
			int newCycleNum = latestCycle == null ? 1 : latestCycle.getCycleNum() + 1;

			LocalDate cycleStartDate = enrollment.getNextBillingDate();

			BillingPeriod billingPeriod;

			if (frequency == Frequency.LEARNING_PERIOD && totalLearningDays != null) {
				billingPeriod = calculateBillingPeriodByLearningDays(cycleStartDate, endDate,
						learningDays, totalLearningDays);
			} else if (frequency != null) {
				billingPeriod = calculateBillingPeriodByFrequency(cycleStartDate, endDate, frequency, learningDays);
			} else {
				throw new IllegalArgumentException("Either frequency or totalLearningDays must be specified.");
			}
			
			BillingCycle newCycle = new BillingCycle();
			newCycle.setCourseCode(course.getCode());
			newCycle.setStudentCode(student.getStudentCode());
			newCycle.setEnrollmentId(enrollment.getId());
			newCycle.setBillingFrequencyOption(frequencyOption);
			newCycle.setGeneratedDate(effDate);
			newCycle.setCycleStartDate(cycleStartDate);
			newCycle.setCycleEndDate(billingPeriod.getCycleEndDate());
			newCycle.setTotalLearningDays(billingPeriod.getTotalLearningDays());
			newCycle.setStatus(BillingCycleStatus.IN_PROGRESS);
			newCycle.setCycleNum(newCycleNum);
			BillingCycle createdCycle = billingCycleRepository.save(newCycle);
			
			enrollment.setNextBillingDate(billingPeriod.getNextCycleStartDate());
			enrollmentRepository.save(enrollment);

			Invoice invoice = new Invoice();
			invoice.setBillingCycle(createdCycle);
			invoice.setCourseCode(course.getCode());
			invoice.setStudentCode(student.getStudentCode());
			invoice.setAmountDue(feeAmount.subtract(totalDiscount));
			invoice.setAmountPaid(BigDecimal.ZERO);
			invoice.setIssueDate(effDate);
			invoice.setPublicDate(null);
			invoice.setDueDate(createdCycle.getCycleStartDate());
			invoice.setStatus(InvoiceStatus.UNPAID);
			invoice.setType(Invoice.getInvoiceTypeByFrequency(frequency));
			invoice.setDescription(null);
			invoiceRepository.save(invoice);

			dto = GenerateBillingResponseDTO.builder().id(createdCycle.getId())
					.enrollmentId(createdCycle.getId())
					.courseCode(course.getCode())
					.studentCode(student.getStudentCode())
					.totalFee(invoice.getAmountDue())
					.totalDiscount(totalDiscount)
					.generatedDate(createdCycle.getGeneratedDate())
					.cycleStartDate(createdCycle.getCycleStartDate())
					.cycleEndDate(createdCycle.getCycleEndDate())
					.nextCycleStartDate(enrollment.getNextBillingDate())
					.frequency(frequency)
					.totalLearningDays(createdCycle.getTotalLearningDays())
					.cycleNum(createdCycle.getCycleNum())
					.status(createdCycle.getStatus()).
					build();
			billingResponse.add(dto);
		}
		return billingResponse;
	}

	private BillingPeriod calculateBillingPeriodByFrequency(LocalDate cycleStartDate, LocalDate endEnrollDate,
			Frequency frequency, Set<DayOfWeek> learningDays) {
		LocalDate nextCycleStartDate;
		switch (frequency) {
		case MONTHLY:
			nextCycleStartDate = cycleStartDate.plusMonths(1);
			break;
		case QUARTERLY:
			nextCycleStartDate = cycleStartDate.plusMonths(3);
			break;
		case ANNUAL:
			nextCycleStartDate = cycleStartDate.plusYears(1);
			break;
		default:
			throw new IllegalArgumentException("Invalid billing duration: " + frequency);
		}
		if (nextCycleStartDate.isAfter(endEnrollDate)) {
			nextCycleStartDate = endEnrollDate;
		}
		LocalDate cycleEndDate = nextCycleStartDate.minusDays(1); // Bill up to the day before the next cycle starts.

		// Calculate total learning days within the cycle
		int totalLearningDays = 0;
		LocalDate currentDate = cycleStartDate;
		while (!currentDate.isAfter(cycleEndDate)) {
			if (learningDays.contains(currentDate.getDayOfWeek())) {
				totalLearningDays++;
			}
			currentDate = currentDate.plusDays(1);
		}

		return new BillingPeriod(cycleEndDate, nextCycleStartDate, totalLearningDays);
	}

	private BillingPeriod calculateBillingPeriodByLearningDays(LocalDate cycleStartDate, LocalDate endEnrollDate,
			Set<DayOfWeek> learningDays, Integer requiredLearningDays) {
		LocalDate currentDate = cycleStartDate;
		int learningDayCount = 0;

		// Count the number of valid learning days.
		while (learningDayCount < requiredLearningDays) {
			if (learningDays.contains(currentDate.getDayOfWeek())) {
				learningDayCount++;
			}
			currentDate = currentDate.plusDays(1);
		} //  loop finishes, currentDate has already been advanced by one day

		LocalDate cycleEndDate = currentDate.minusDays(1);
		if (cycleEndDate.isAfter(endEnrollDate)) {
		    cycleEndDate = endEnrollDate;
		    return new BillingPeriod(cycleEndDate, GlobalConstants.MAX_DATE, learningDayCount);
		}

		LocalDate nextCycleStartDate = cycleEndDate.plusDays(1);
		// Find the next valid learning day after the cycleEndDate
		while (!learningDays.contains(nextCycleStartDate.getDayOfWeek())) {
			nextCycleStartDate = nextCycleStartDate.plusDays(1);
		}

		return new BillingPeriod(cycleEndDate, nextCycleStartDate, learningDayCount);
	}

	@Transactional
	public List<CollectionResponseDTO> processCollections(CollectionRequestDTO request) {
		List<CollectionResponseDTO> collectionResponse = new ArrayList<>();
		effDate = request.getEffDate() != null ? request.getEffDate() : LocalDate.now();
		fromStudentCode = request.getFromStudentCode() != "" ? request.getFromStudentCode() : "st00000";
		toStudentCode = request.getToStudentCode() != "" ? request.getFromStudentCode() : "st99999";
		
		List<BillingCycle> billingCycles = billingCycleRepository.findAllByStatusAndStudentCodeRangeAndGeneratedDate(
			BillingCycleStatus.IN_PROGRESS, fromStudentCode, toStudentCode, effDate);
		
		for (BillingCycle billingCycle : billingCycles) {
			List<Invoice> invoices = processInvoicesForBillingCycle(billingCycle, collectionResponse);
			updateBillingCycleStatus(billingCycle, invoices);
		}
		
		return collectionResponse;
	}

	private List<Invoice> processInvoicesForBillingCycle(BillingCycle billingCycle, List<CollectionResponseDTO> collectionResponse) {
		List<Invoice> invoices = billingCycle.getInvoices().stream()
			    .filter(invoice -> invoice.getStatus() == InvoiceStatus.PARTIALLY_PAID 
			                    || invoice.getStatus() == InvoiceStatus.UNPAID)
			    .collect(Collectors.toList());
		
		CollectionResponseDTO collection = new CollectionResponseDTO();
		collection.setPayments(new ArrayList<>());
		collection.setCourseCode(billingCycle.getCourseCode());
		collection.setStudentCode(billingCycle.getStudentCode());
		collection.setCycleStartDate(billingCycle.getCycleStartDate());
		collection.setCycleEndDate(billingCycle.getCycleEndDate());
		collection.setCycleNum(billingCycle.getCycleNum());

		for (Invoice invoice : invoices) {
			StudentAccount studentAccount = studentAccountService.findByStudentCode(invoice.getStudentCode());

			BigDecimal amountToPay = calculatePaymentAmount(invoice, studentAccount);
			if (amountToPay.compareTo(BigDecimal.ZERO) == 0) {
				continue;
			}
			updateInvoice(invoice, amountToPay);
			updateStudentAccount(studentAccount, amountToPay);
			Payment payment = createPayment(invoice, studentAccount, amountToPay);
			collection.getPayments().add(payment);
			collectionResponse.add(collection);
		}

		return invoices;
	}

	private BigDecimal calculatePaymentAmount(Invoice invoice, StudentAccount studentAccount) {
		return invoice.getAmountDue().min(studentAccount.getBalanceAmount());
	}

	private void updateInvoice(Invoice invoice, BigDecimal amountToPay) {
		invoice.setAmountPaid(invoice.getAmountPaid().add(amountToPay));
		invoice.setAmountDue(invoice.getAmountDue().subtract(amountToPay));

		if (invoice.getAmountDue().compareTo(BigDecimal.ZERO) == 0) {
			invoice.setStatus(InvoiceStatus.PAID);
		} else if (invoice.getAmountPaid().compareTo(BigDecimal.ZERO) == 0) {
			invoice.setStatus(InvoiceStatus.UNPAID);
		} else {
			invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
		}

		invoiceRepository.save(invoice);
	}

	private void updateStudentAccount(StudentAccount studentAccount, BigDecimal amountToPay) {
		studentAccount.setBalanceAmount(studentAccount.getBalanceAmount().subtract(amountToPay));
		studentAccountService.save(studentAccount);
	}

	private Payment createPayment(Invoice invoice, StudentAccount studentAccount, BigDecimal amountToPay) {
		Payment payment = new Payment();
		payment.setInvoice(invoice);
		payment.setStudentAccount(studentAccount);
		payment.setPaymentDate(LocalDate.now());
		payment.setAmount(amountToPay);

		return paymentRepository.save(payment);
	}


	private void updateBillingCycleStatus(BillingCycle billingCycle, List<Invoice> invoices) {
		boolean allPaid = invoices.stream().allMatch(invoice -> invoice.getStatus() == InvoiceStatus.PAID);
		boolean hasOverdue = invoices.stream().anyMatch(invoice -> 
			(invoice.getStatus() == InvoiceStatus.PARTIALLY_PAID || invoice.getStatus() == InvoiceStatus.UNPAID) &&
			invoice.getDueDate().isBefore(effDate));

		if (allPaid) {
			billingCycle.setStatus(BillingCycleStatus.COMPLETED);
		} else if (hasOverdue) {
			billingCycle.setStatus(BillingCycleStatus.OVERDUE);
		} else {
			billingCycle.setStatus(BillingCycleStatus.IN_PROGRESS);
		}

		billingCycleRepository.save(billingCycle);
	}

	// Method to retrieve Billings for invoice generation
	public List<BillingCycle> getBillingsForInvoiceGeneration(Long fromStudentCode, Long toStudentCode,
			LocalDate toDate) {
		return null;// billingCycleRepository.findBillingCyclesForStudentRangeWithDueDate(fromStudentCode,
					// toStudentCode, toDate);
	}

	public Page<BillingCycle> getFilteredBillingCycles(BillingCycleStatus status, String StudentCode, Long enrollmentId,
			Integer cycleNum, LocalDate startDate, LocalDate endDate, int page, int size) {
		Specification<BillingCycle> spec = Specification.where(BillingCycleSpecification.hasStatus(status))
				.and(BillingCycleSpecification.hasStudentCode(StudentCode))
				.and(BillingCycleSpecification.hasEnrollmentId(enrollmentId))
				.and(BillingCycleSpecification.hasCycleNum(cycleNum))
				.and(BillingCycleSpecification.hasCycleStartDateBetween(startDate, endDate));

		return billingCycleRepository.findAll(spec, PageRequest.of(page, size));
	}

	@Getter
	@Setter
	@AllArgsConstructor
	private static class BillingPeriod {
		private final LocalDate cycleEndDate;
		private final LocalDate nextCycleStartDate;
		private final Integer totalLearningDays;
	}

}
