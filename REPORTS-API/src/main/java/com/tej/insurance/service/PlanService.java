package com.tej.insurance.service;

import java.net.http.HttpResponse;
import java.util.List;

import com.tej.insurance.response.SearchRequestDto;
import com.tej.insurance.response.SearchResponseDto;

import jakarta.servlet.http.HttpServletResponse;

public interface PlanService {
	
	public List<String> getUniquePlanNames();
	
	public List<String> getUniquePlanStatuses();
	
	public List<SearchResponseDto> Search(SearchRequestDto requestDto);
	
	public void generateExcel(HttpServletResponse response) throws Exception;
	
	public void generatePDF(HttpServletResponse response) throws Exception;

}
