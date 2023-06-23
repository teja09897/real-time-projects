package com.tej.insurance.response;

import java.time.LocalDate;

import lombok.Data;
@Data
public class SearchRequestDto {
	private String planName;
	private String planStatus;
	private LocalDate startDate;
	private LocalDate endDate;

}
