package com.cheor.wanted_10.recruitment.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheor.wanted_10.base.rsData.RsData;
import com.cheor.wanted_10.recruitment.dto.RecruitmentDTO;
import com.cheor.wanted_10.recruitment.entyty.Recruitment;
import com.cheor.wanted_10.recruitment.service.RecruitmentService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recruitment")
public class RecruitmentController {
	private final RecruitmentService recruitmentService;

	@AllArgsConstructor
	@Getter
	@NoArgsConstructor
	public static class RecruitmentResponse {
		private String companyName;
		private String position;
		private Integer reward;

		private String content;
		private String skill;
		@JsonCreator
		public RecruitmentResponse(Recruitment recruitment) {
			this.companyName = recruitment.getCompany().getName();
			this.position = recruitment.getPosition();
			this.reward = recruitment.getReward();
			this.content = recruitment.getContent();
			this.skill = recruitment.getSkill();
		}
	}

	@PostMapping("/register")
	public RsData<RecruitmentResponse> register(@Valid @RequestBody RecruitmentDTO recruitmentDTO) {

		RsData<Recruitment> rsData = recruitmentService.create(recruitmentDTO);
		if(rsData.isFail()) {
			return (RsData)rsData;
		}
		return RsData.of(rsData.getResultCode(), rsData.getMsg(), new RecruitmentResponse(rsData.getData()));
	}
}
