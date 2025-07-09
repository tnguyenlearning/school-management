//package com.school.billing.batchjobs.billproc.service;
//
//import java.util.Iterator;
//
//import org.springframework.batch.item.ItemReader;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import com.school.billing.entities.BillingHeader;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class BillingReader implements ItemReader<BillingHeader> {
//
//	private final WebClient webClient;
//	private Iterator<BillingHeader> billingHeaders;
//	@Value("${school.services.education.uri}")
//	private String uri;
//
//	@Override
//	public BillingHeader read() {
//		if (billingHeaders == null) {
//			billingHeaders = webClient.get().uri(uri).retrieve().bodyToFlux(BillingHeader.class).toStream().iterator();
//		}
//		if (billingHeaders.hasNext()) {
//			return billingHeaders.next();
//		}
//		return null;
//	}
//}
