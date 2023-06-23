package com.tej.insurance.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tej.insurance.entity.PlanEligibilityDetails;
import com.tej.insurance.response.SearchRequestDto;
import com.tej.insurance.response.SearchResponseDto;
import com.tej.insurance.service.PlanService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class PlanDetailsRestController {
	@Autowired
	private PlanService planService;
	
	@GetMapping("/planes")
	public ResponseEntity<List<String>> getPlanNames() {
		List<String> planNames = planService.getUniquePlanNames();
		return new ResponseEntity(planNames, HttpStatus.OK);
	}
	
	@GetMapping("/status")
	public ResponseEntity<List<String>> getPlanStatus() {
		List<String> statuses = planService.getUniquePlanStatuses();
		return new ResponseEntity<List<String>>(statuses, HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<PlanEligibilityDetails>> search(@RequestBody SearchRequestDto requestDto) {
		List<SearchResponseDto> search = planService.Search(requestDto);
		return new ResponseEntity(search, HttpStatus.OK);
	}
	
	@GetMapping("/Excel")
	public void generateExcel(HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");
		String headKey="Content-disposition";
		String headValue="attachment;filename=data.xls";
		response.setHeader(headKey, headValue);
		planService.generateExcel(response);
	}
	
	@GetMapping("/pdf")
	public void PDF(HttpServletResponse response) throws Exception{
		response.setContentType("application/pdf");
		String headKey="Content-disposition";
		String headValue="attachment;filename=data.pdf";
		response.setHeader(headKey, headValue);
		planService.generatePDF(response);
	}
	

}
