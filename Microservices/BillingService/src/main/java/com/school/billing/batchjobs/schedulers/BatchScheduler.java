//package com.school.billing.schedulers;
//
//import java.time.LocalDate;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.school.billing.services.BatchService;
//
//@Component
//public class BatchScheduler {
//	@Autowired
//	private BatchService batchService;
//
//	@Scheduled(cron = "0 0 * * * ?") // Schedule at midnight daily
//	public void performBatchJob() throws Exception {
//		batchService.runBatchJob("billproc", LocalDate.now());
//	}
//}


