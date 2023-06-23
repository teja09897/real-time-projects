package com.tej.insurance.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tej.insurance.entity.PlanEligibilityDetails;

public interface PlanEligibilityRepo extends JpaRepository<PlanEligibilityDetails, Integer>{


	@Query("select distinct(planName) from PlanEligibilityDetails")
	List<String> findPlanNames();

	@Query("select distinct(planStatus) from PlanEligibilityDetails")
	List<String> findPlanStatus();

	

	

}
