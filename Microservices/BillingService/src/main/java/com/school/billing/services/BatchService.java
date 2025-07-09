//package com.school.billing.services;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.JobParametersInvalidException;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
//import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
//import org.springframework.batch.core.repository.JobRestartException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BatchService {
//	
//	@Autowired
//	private JobLauncher jobLauncher;
//	@Autowired
//	private Job billingProcessing;
//
//	public void runBatchJob(String jobName, LocalDate effdate) throws JobExecutionAlreadyRunningException,
//			JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
//		JobParameters params = new JobParametersBuilder().addString("JOB-NAME", jobName).addDate("DATIME", new Date())
//				.addLocalDate("EFFDATE", effdate).toJobParameters();
//		switch (jobName) {
//		case "billproc": {
//			jobLauncher.run(billingProcessing, params);
//			break;
//		}
//		default:
//			break;
//		}
//
//	}
//
//	public LocalDate convertEffdateString(String effdate) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		return LocalDate.parse(effdate, formatter);
//	}
//	
//}
