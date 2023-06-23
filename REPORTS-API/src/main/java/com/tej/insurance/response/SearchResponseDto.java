package com.tej.insurance.response;

import lombok.Data;

@Data
public class SearchResponseDto {
	private Integer eligibilityId;
    private String name;
    private Long mobileNo;
    private String email;
    private Character gender;
    private String ssn;
}
