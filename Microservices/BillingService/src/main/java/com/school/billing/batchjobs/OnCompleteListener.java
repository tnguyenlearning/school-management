//package com.school.billing.listeners;
//
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobExecutionListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class OnCompleteListener implements JobExecutionListener{
//	
//	public void afterJob(JobExecution jobExecution) {
//		switch (jobExecution.getStatus()) {
//		case COMPLETED: {
//			System.out.println("Batch completed");
//			break;
//		}
//		default:
//			throw new IllegalArgumentException("Unexpected value: " + jobExecution.getStatus());
//		}
//	}
//}
package com.school.billing.batchjobs;


