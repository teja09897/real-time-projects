package com.tej.insurance.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.tej.insurance.entity.PlanEligibilityDetails;
import com.tej.insurance.repo.PlanEligibilityRepo;

@Component
public class AppRunner implements ApplicationRunner{
	@Autowired
	private PlanEligibilityRepo eligibilityRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		PlanEligibilityDetails details=new PlanEligibilityDetails();
		details.setEligibilityId(1);
		details.setName("Ravi");
		details.setEmail("tej@123");
		details.setSsn("BNM89876");
		details.setGender('M');
		details.setMobileNo(7699888886l);
		details.setPlanStatus("Approved");
		details.setPlanName("SSD");
		eligibilityRepo.save(details);
		
		PlanEligibilityDetails details2=new PlanEligibilityDetails();
		details2.setEligibilityId(2);
		details2.setName("Vinay");
		details2.setEmail("vny@123");
		details2.setGender('M');
		details2.setSsn("IUY89876");
		details2.setMobileNo(8765766608l);
		details2.setPlanStatus("Denied");
		details2.setPlanName("CCD");
		eligibilityRepo.save(details2);
		
		PlanEligibilityDetails details3=new PlanEligibilityDetails();
		details3.setEligibilityId(3);
		details3.setName("kuyyabro");
		details3.setEmail("kuyya@123");
		details3.setGender('F');
		details3.setMobileNo(9876543212l);
		details3.setSsn("FGH89876");
		details3.setPlanStatus("Approved");
		details3.setPlanName("Medical");
		eligibilityRepo.save(details3);
		
	}

}
