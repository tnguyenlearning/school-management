package com.school.student.education.dto;

import java.math.BigDecimal;

import com.school.constant.Frequency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseFrequencyOptionDTO {

    private Long id;
    private Frequency frequency;
    private Integer totalLearningDays;
    private BigDecimal feeAmount;
    
}
