//package com.school.billing.batchjobs.billproc.service;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.util.Set;
//
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.stereotype.Service;
//
//import com.school.billing.batchjobs.billproc.dto.BillingCycleDTO;
//import com.school.billing.batchjobs.billproc.dto.CourseDTO;
//import com.school.billing.batchjobs.billproc.dto.EnrollmentDTO;
//import com.school.billing.batchjobs.billproc.dto.GenerateBillingDTO;
//import com.school.billing.constants.BillingCycleStatus;
//import com.school.billing.constants.Frequency;
//import com.school.billing.entities.BillingCycle;
//
//@Service 
//// note: why don't use @Scope("step")
//public class BillingProcessor implements ItemProcessor<GenerateBillingDTO, BillingCycle> {
//	
//	// @Value("#{jobParameters['EFFDATE']}")
//    private LocalDate effdate;
//	
//	@Override
//	public BillingCycleDTO process(EnrollmentDTO enrollment) throws Exception {
//		// TODO Auto-generated method stub
//		// Calculate course fee based on business
//		CourseDTO course = enrollment.course();
//		Set<DayOfWeek> learningDays = course.daysOfWeek();
//
//		Frequency frequency = enrollment.frequency();
//		Integer totalLearningDays = enrollment.totalLearningDays();
//
//		// Get latest billing cycle by enrollment id (RE-UPDATE)
//		BillingCycleDTO latestCycle = null;
//		LocalDate lastBillingDate = latestCycle == null ? enrollment.startDate() : latestCycle.nextBillingDate();
//
//		// Calculate billing period using either frequency or totalLearningDays.
//		BillingPeriod billingPeriod;
//
//		if (frequency == Frequency.LEARNING_PERIOD && totalLearningDays != null) {
//			billingPeriod = calculateBillingPeriodByLearningDays(lastBillingDate, enrollment.endDate(), learningDays,
//					totalLearningDays);
//		} else if (frequency != null) {
//			billingPeriod = calculateBillingPeriodByFrequency(lastBillingDate, enrollment.endDate(), frequency);
//		} else {
//			throw new IllegalArgumentException("Either frequency or totalLearningDays must be specified.");
//		}
//		BillingCycleDTO newBillingCycle = new BillingCycleDTO(enrollment, billingPeriod.nextBillingDate(), lastBillingDate, enrollment.student(),
//				effdate, null, BillingCycleStatus.UN_PAID, enrollment.frequency());
//		return newBillingCycle;
//	}
//
//	private BillingPeriod calculateBillingPeriodByFrequency(LocalDate startDate, LocalDate endDate,
//			Frequency frequency) {
//		LocalDate nextBillingDate;
//		switch (frequency) {
//		case MONTHLY:
//			nextBillingDate = startDate.plusMonths(1);
//			break;
//		case QUARTERLY:
//			nextBillingDate = startDate.plusMonths(3);
//			break;
//		case ANNUAL:
//			nextBillingDate = startDate.plusYears(1);
//			break;
//		default:
//			throw new IllegalArgumentException("Invalid billing duration: " + frequency);
//		}
//		if (nextBillingDate.isAfter(endDate)) {
//			nextBillingDate = endDate;
//		}
//		LocalDate billToDate = nextBillingDate.minusDays(1); // Bill up to the day before the next cycle starts.
//		return new BillingPeriod(billToDate, nextBillingDate, null); // No learning days calculated here.
//	}
//
//	private BillingPeriod calculateBillingPeriodByLearningDays(LocalDate startDate, LocalDate endDate,
//			Set<DayOfWeek> learningDays, Integer requiredLearningDays) {
//		LocalDate currentDate = startDate;
//		int learningDayCount = 0;
//
//		// Count the number of valid learning days.
//		while (learningDayCount < requiredLearningDays) {
//			if (learningDays.contains(currentDate.getDayOfWeek())) {
//				learningDayCount++;
//			}
//			currentDate = currentDate.plusDays(1);
//		}
//		if (currentDate.isAfter(endDate)) {
//			currentDate = endDate;
//		}
//		LocalDate billToDate = currentDate.minusDays(1);
//		return new BillingPeriod(billToDate, currentDate, learningDayCount);
//	}
//
//	private static record BillingPeriod(LocalDate billToDate, LocalDate nextBillingDate, Integer totalLearningDays) {
//	}
//
//}
