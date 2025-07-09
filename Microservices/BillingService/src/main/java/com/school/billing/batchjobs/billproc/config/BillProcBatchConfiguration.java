//package com.school.billing.batchjobs.billproc.config;
//
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class BillProcBatchConfiguration {
////	@Autowired
////	private BillingReader billingReader;
////	@Autowired
////	private BillingProcessor billingProcessor;
////	@Autowired
////	private BillingWriter billingWriter;
////	@Autowired
////	private OnCompleteListener onCompleteListener;
////
////	@Bean
////	Step billingGeneratingStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
////		return new StepBuilder("billingGeneration", jobRepository)
////				.<EnrollmentDTO, BillingCycleDTO>chunk(10, transactionManager).reader(billingReader)
////				.processor(billingProcessor).writer(billingWriter).build();
////	}
////
//////	@Bean
//////	Step overdueStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//////		return new StepBuilder("overdueStep", jobRepository)
//////				.<EnrollmentDTO, BillingCycleDTO>chunk(10, transactionManager).reader(billingReader)
//////				.processor(billingProcessor).writer(billingWriter).build();
//////	}
////
////	@Bean
////	Job billingJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
////		return new JobBuilder("billingProcessing", jobRepository).incrementer(new RunIdIncrementer())
////				.listener(onCompleteListener).flow(billingGeneratingStep(jobRepository, transactionManager)).end()
////				.build();
////	}
//}
