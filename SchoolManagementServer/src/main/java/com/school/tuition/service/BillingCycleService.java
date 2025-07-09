package com.school.tuition.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.school.constant.BillingAction;
import com.school.constant.BillingCycleStatus;
import com.school.constant.EnrollmentStatus;
import com.school.constant.Frequency;
import com.school.student.education.entity.Course;
import com.school.student.education.service.EnrollmentService;
import com.school.student.enrollment.entity.Enrollment;
import com.school.student.enrollment.entity.Student;
import com.school.tuition.dto.GenerateBillingRequestDTO;
import com.school.tuition.dto.GenerateBillingResponseDTO;
import com.school.tuition.entity.BillingCycle;
import com.school.tuition.entity.CourseFrequencyOption;
import com.school.tuition.entity.EnrollmentDiscount;
import com.school.tuition.repository.BillingCycleRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class BillingCycleService {

	private final BillingCycleRepository billingCycleRepository;
	private EnrollmentService enrollmentService;
	private final InvoiceService invoiceService;
	private final EnrollmentDiscountService enrollmentDiscountService;

	@Autowired
	public void setEnrollmentService(@Lazy EnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}

	@Transactional
	public List<GenerateBillingResponseDTO> generateBillingForRangeStudents(GenerateBillingRequestDTO request) {
		List<GenerateBillingResponseDTO> billingResponseList = new ArrayList<>();
		LocalDate effDate = request.getEffDate();
		GenerateBillingResponseDTO response = null;
		if (effDate == null) {
			// get User date
		}
		// Get all active enrollments for the student
		List<Enrollment> enrollments = enrollmentService.getFilteredEnrollmentsForBilling(request.getFromStudentId(),
				request.getToStudentId(), EnrollmentStatus.ACTIVE, effDate);

		for (Enrollment enrollment : enrollments) {
			Course course = enrollment.getCourse();
			Student student = enrollment.getStudent();
			Set<DayOfWeek> learningDays = course.getDaysOfWeek();

			CourseFrequencyOption frequencyOption = enrollment.getFrequencyOption();
			Frequency frequency = frequencyOption.getFrequency();
			Integer totalLearningDays = frequencyOption.getTotalLearningDays();
			BigDecimal basicFeeAmount = frequencyOption.getFeeAmount();
			List<EnrollmentDiscount> enrollmentDiscounts = enrollment.getEnrollmentDiscounts();
			BigDecimal totalDiscount = enrollmentDiscountService.calculateTotalDiscountAmount(basicFeeAmount, effDate,
					enrollmentDiscounts);

			BillingCycle latestCycle = billingCycleRepository
					.findTopByEnrollmentIdOrderByCycleNumDesc(enrollment.getId()).orElse(null);
			LocalDate cycleStartDate = latestCycle == null ? enrollment.getStartDate()
					: latestCycle.getNextCycleStartDate();
			int newCycleNum = latestCycle == null ? 1 : latestCycle.getCycleNum() + 1;

			// Calculate billing period using either frequency or totalLearningDays.
			BillingPeriod billingPeriod;

			if (frequency == Frequency.LEARNING_PERIOD && totalLearningDays != null) {
				billingPeriod = calculateBillingPeriodByLearningDays(cycleStartDate, enrollment.getEndDate(),
						learningDays, totalLearningDays);
			} else if (frequency != null) {
				billingPeriod = calculateBillingPeriodByFrequency(cycleStartDate, enrollment.getEndDate(), frequency);
			} else {
				throw new IllegalArgumentException("Either frequency or totalLearningDays must be specified.");
			}

			// Create the new billing cycle.
			BillingCycle newCycle = new BillingCycle();
			newCycle.setEnrollment(enrollment);
			newCycle.setStudent(student);
			newCycle.setFrequency(frequency);
			newCycle.setTotalLearningDays(billingPeriod.getTotalLearningDays());
			newCycle.setGeneratedDate(effDate);
			newCycle.setCycleStartDate(cycleStartDate);
			newCycle.setCycleEndDate(billingPeriod.getCycleEndDate());
			newCycle.setNextCycleStartDate(billingPeriod.getNextCycleStartDate());
			newCycle.setTotalFee(basicFeeAmount);
			newCycle.setTotalDiscount(totalDiscount);
			newCycle.setRemainingBalance(newCycle.calcRemainingBalance());
			newCycle.setStatus(BillingCycleStatus.OPEN);
			newCycle.setCycleNum(newCycleNum);

			BillingCycle generatedBilling = billingCycleRepository.save(newCycle);

			invoiceService.createInvoiceFrombilling(generatedBilling);

			response = GenerateBillingResponseDTO.builder().id(generatedBilling.getId())
					.enrollmentId(enrollment.getId()).courseId(course.getId()).courseCode(course.getCode())
					.studentId(student.getId()).studentCode(student.getStudentCode())
					.totalFee(generatedBilling.getTotalFee()).totalDiscount(generatedBilling.getTotalDiscount())
					.totalRefund(generatedBilling.getTotalRefund()).generatedDate(generatedBilling.getGeneratedDate())
					.cycleStartDate(generatedBilling.getCycleStartDate())
					.cycleEndDate(generatedBilling.getCycleEndDate())
					.nextCycleStartDate(generatedBilling.getNextCycleStartDate())
					.frequency(generatedBilling.getFrequency())
					.totalLearningDays(generatedBilling.getTotalLearningDays()).cycleNum(generatedBilling.getCycleNum())
					.status(generatedBilling.getStatus()).build();
			billingResponseList.add(response);

			LocalDate nextGenerateBillingDate = newCycle.getNextCycleStartDate().minusDays(7);
			enrollment.setNextGenerateBillingDate(nextGenerateBillingDate);
			enrollment.setBillToDate(billingPeriod.getCycleEndDate());
			enrollmentService.save(enrollment);

		}
		return billingResponseList;
	}

	private BillingPeriod calculateBillingPeriodByFrequency(LocalDate cycleStartDate, LocalDate endEnrollDate,
			Frequency frequency) {
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
		return new BillingPeriod(cycleEndDate, nextCycleStartDate, null); // No learning days calculated here.
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
		}

		if (currentDate.isAfter(endEnrollDate)) {
			currentDate = endEnrollDate;
		}
		LocalDate cycleEndDate = currentDate;

		LocalDate nextCycleStartDate = cycleEndDate.plusDays(1); // Start the next cycle the day after the current cycle
																	// ends

		// Find the next valid learning day after the cycleEndDate
		while (!learningDays.contains(nextCycleStartDate.getDayOfWeek())) {
			nextCycleStartDate = nextCycleStartDate.plusDays(1);
		}

		return new BillingPeriod(cycleEndDate, nextCycleStartDate, learningDayCount);
	}

	// Method to retrieve Billings for invoice generation
	public List<BillingCycle> getBillingsForInvoiceGeneration(Long fromStudentId, Long toStudentId, LocalDate toDate) {
		return billingCycleRepository.findBillingCyclesForStudentRangeWithDueDate(fromStudentId, toStudentId, toDate);
	}

	@Getter
	@Setter
	@AllArgsConstructor
	private static class BillingPeriod {
		private final LocalDate cycleEndDate;
		private final LocalDate nextCycleStartDate;
		private final Integer totalLearningDays;
	}

	public LocalDate calcNextGenerateBillingDate(Enrollment enrollment, BillingAction billingAction) {
		switch (billingAction) {
		case CREATE:
			return enrollment.getStartDate().minusDays(7);

		default:
			throw new IllegalArgumentException("Invalid Action: " + billingAction);
		}
	}

	public Page<BillingCycle> getFilteredBillingCycles(BillingCycleStatus status, Long studentId, Long enrollmentId,
			Integer cycleNum, LocalDate startDate, LocalDate endDate, int page, int size) {
		Specification<BillingCycle> spec = Specification.where(BillingCycleSpecification.hasStatus(status))
				.and(BillingCycleSpecification.hasStudentId(studentId))
				.and(BillingCycleSpecification.hasEnrollmentId(enrollmentId))
				.and(BillingCycleSpecification.hasCycleNum(cycleNum))
				.and(BillingCycleSpecification.hasCycleStartDateBetween(startDate, endDate));

		return billingCycleRepository.findAll(spec, PageRequest.of(page, size));
	}

}
