package com.tej.insurance.service.impl;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout.Alignment;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.tej.insurance.entity.PlanEligibilityDetails;
import com.tej.insurance.repo.PlanEligibilityRepo;
import com.tej.insurance.response.SearchRequestDto;
import com.tej.insurance.response.SearchResponseDto;
import com.tej.insurance.service.PlanService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
@Service
public class PlanServiceImpl implements PlanService{
	
	@Autowired
	private PlanEligibilityRepo eligibilityRepo;

	@Override
	public List<String> getUniquePlanNames() {    	
		return eligibilityRepo.findPlanNames();
	}

	@Override
	public List<String> getUniquePlanStatuses() {
		return eligibilityRepo.findPlanStatus();
	}

	@Override
	public List<SearchResponseDto> Search(SearchRequestDto requestDto) {
//		List<PlanEligibilityDetails> reports = eligibilityRepo.findAll();
//			List<PlanEligibilityDetails> collect = reports.stream().filter(e->e.getName().equals(requestDto.getPlanName()))
//			                .filter(e->e.getPlanStatus().equals(requestDto.getPlanStatus()))
//			                .filter(e->e.getPlanEndDate().equals(requestDto.getStartDate()))
//			                .filter(e->e.getPlanEndDate().equals(requestDto.getEndDate())).collect(Collectors.toList());
//		
//		Function<PlanEligibilityDetails, SearchResponseDto> response=e->{
//			  SearchResponseDto responseDto=new SearchResponseDto();  
//			BeanUtils.copyProperties(e, responseDto);
//			return responseDto;	
//		};
//		List<SearchResponseDto> collect2 = collect.stream().map(response).collect(Collectors.toList());
//		return collect2;
		List<SearchResponseDto> response=new ArrayList<>();
		
		PlanEligibilityDetails queryBuilders=new PlanEligibilityDetails();
		String planName = requestDto.getPlanName();
		if (planName!=null && !planName.equals("")) {
			queryBuilders.setPlanName(planName);
		}
		
		String planStatus = requestDto.getPlanStatus();
		if (planStatus!=null && !planName.equals("")) {
			queryBuilders.setPlanStatus(planStatus);
		}
		
		LocalDate startDate = requestDto.getStartDate();
		if (startDate!=null) {
			queryBuilders.setPlanStartDate(startDate);
		}
		
		 LocalDate endDate = requestDto.getEndDate();
		 if (endDate!=null) {
			queryBuilders.setPlanEndDate(endDate);
		}
		 
		 Example<PlanEligibilityDetails> example = Example.of(queryBuilders);
		 
		List<PlanEligibilityDetails> entities = eligibilityRepo.findAll(example);
		for (PlanEligibilityDetails planEligibilityDetails : entities) {
			SearchResponseDto searchResponseDto=new SearchResponseDto();
			BeanUtils.copyProperties(planEligibilityDetails, searchResponseDto);
			response.add(searchResponseDto);
		}
		return response;
		
	}

	@Override
	public void generateExcel(HttpServletResponse response) throws Exception{
		List<PlanEligibilityDetails> eligibilityInfo = eligibilityRepo.findAll();
		HSSFWorkbook workbook =new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = sheet.createRow(0);
		
		row.createCell(0).setCellValue("name");
		row.createCell(1).setCellValue("email");
		row.createCell(2).setCellValue("mobile");
		row.createCell(3).setCellValue("Gender");
		row.createCell(4).setCellValue("ssn");
		
		
		int i=1;
		for (PlanEligibilityDetails entity : eligibilityInfo) {
			
			HSSFRow dataRow = sheet.createRow(i);
			dataRow.createCell(0).setCellValue(entity.getName());
			dataRow.createCell(1).setCellValue(entity.getEmail());
			dataRow.createCell(2).setCellValue(entity.getMobileNo());
			dataRow.createCell(3).setCellValue(String.valueOf(entity.getGender()));
			dataRow.createCell(4).setCellValue(entity.getSsn());
			i++;
		}
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
	}

	@Override
	public void generatePDF(HttpServletResponse response) throws DocumentException, IOException {
		List<PlanEligibilityDetails> entities = eligibilityRepo.findAll();
		Document document=new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		
		Paragraph p=new Paragraph("Search Reports",font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		
		
		PdfPTable table=new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] {2.0f,3.5f,3.0f,1.0f,2.5f});
		table.setSpacingBefore(10);
		
		PdfPCell cell=new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		cell.setPhrase(new Phrase("Name"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Email"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Mobile"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Gender"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("SSN"));
		table.addCell(cell);
		
		for (PlanEligibilityDetails eligibilityInfo : entities) {
			table.addCell(eligibilityInfo.getName());
			table.addCell(eligibilityInfo.getEmail());
			table.addCell(String.valueOf(eligibilityInfo.getMobileNo()));
			table.addCell(String.valueOf(eligibilityInfo.getGender()));
			table.addCell(eligibilityInfo.getSsn());
		}
		document.add(table);
		document.close();		
	}

}
