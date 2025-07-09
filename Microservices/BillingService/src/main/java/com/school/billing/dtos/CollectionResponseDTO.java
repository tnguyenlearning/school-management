package com.school.billing.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.school.billing.entities.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectionResponseDTO {

	private String courseCode;
	private String studentCode;

	private LocalDate cycleStartDate;
	private LocalDate cycleEndDate;
	
	private Integer cycleNum;

	List<Payment> payments;

}
