//package com.school.billing.controllers;
//
//import org.springframework.web.bind.annotation.RestController;
//
//import com.school.billing.services.BatchService;
//
//import org.springframework.batch.core.JobParametersInvalidException;
//import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
//import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
//import org.springframework.batch.core.repository.JobRestartException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@RestController
//@RequestMapping("/batch")
//public class BatchController {
//	@Autowired
//	private BatchService batchService;
//	
//	@PostMapping("/{jobName}")
//	public String submitBatchJob(@PathVariable String jobName, @RequestParam String effdate) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
//		// TODO: process POST request
//		batchService.runBatchJob(jobName, batchService.convertEffdateString(effdate));
//		return "Batch submitted";
//	}
//}
