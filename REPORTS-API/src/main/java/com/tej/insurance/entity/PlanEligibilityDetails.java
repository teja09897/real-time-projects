package com.tej.insurance.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
@Entity
public class PlanEligibilityDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eligibilityId;
    private String name;
    private long mobileNo;
    private String email;
    private Character gender;
    private String ssn;
    private String planName;
    private String planStatus;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    
    private LocalDate createDate;
    private LocalDate updateDate;
    private String createdBy;
    private String updatedBy;
    
    
}
