package com.cheor.wanted_10.recruitment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentModifyDTO {
	private String position;
	private Integer reward;
	private String content;
	private String skill;
}
