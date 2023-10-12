package com.cheor.wanted_10.recruitment.dto;

import com.cheor.wanted_10.recruitment.entyty.Recruitment;
import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentDTO {
	@NotBlank(message = "서비스에 등록된 회사명을 입력해주세요.")
	private String companyName;
	@NotBlank(message = "채용포지션을 입력해주세요")
	private String position;

	@NotNull(message = "채용보상금을 입력해주세요. 없으면 0")
	private Integer reward;

	@NotBlank(message = "채용내용을 입력헤주세요.")
	private String content;

	@NotBlank(message = "사용기술을 입력해주세요")
	private String skill;

}