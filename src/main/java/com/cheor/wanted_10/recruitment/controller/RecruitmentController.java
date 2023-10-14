package com.cheor.wanted_10.recruitment.controller;

import static org.springframework.http.MediaType.*;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheor.wanted_10.base.rsData.RsData;
import com.cheor.wanted_10.recruitment.dto.RecruitmentDTO;
import com.cheor.wanted_10.recruitment.dto.RecruitmentModifyDTO;
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
		if (rsData.isFail()) {
			return (RsData)rsData;
		}
		return RsData.of(rsData.getResultCode(), rsData.getMsg(), new RecruitmentResponse(rsData.getData()));
	}

	@AllArgsConstructor
	@Getter
	@NoArgsConstructor
	public static class ModifyResponse {
		private String companyName;
		private String position;
		private Integer reward;
		private String content;
		private String skill;

		@JsonCreator
		public ModifyResponse(Recruitment recruitment) {
			this.companyName = recruitment.getCompany().getName();
			this.position = recruitment.getPosition();
			this.reward = recruitment.getReward();
			this.content = recruitment.getContent();
			this.skill = recruitment.getSkill();
		}
	}

	@PatchMapping(value = "/modify/{id}")
	public RsData<ModifyResponse> modify(@PathVariable Long id,
		@RequestBody RecruitmentModifyDTO recruitmentModifyDTO) {
		RsData<Recruitment> rsData = recruitmentService.modify(id, recruitmentModifyDTO);
		if (rsData.isFail()) {
			return (RsData)rsData;
		}
		return RsData.of(rsData.getResultCode(), rsData.getMsg(), new ModifyResponse(rsData.getData()));
	}

	@DeleteMapping("/{id}")
	public RsData delete(@PathVariable Long id) {
		RsData rsData = recruitmentService.delete(id);
		return rsData;
	}

	@AllArgsConstructor
	@Getter
	public static class RecruitmentsResponse {
		private final List<Recruitment> recruitments;
	}

	@GetMapping("/list")
	public RsData<RecruitmentsResponse> readAll() {
		List<Recruitment> recruitments = recruitmentService.getAll();

		return RsData.of(
			"S-1",
			"공고 조회",
			new RecruitmentsResponse(recruitments)
		);
	}

	@GetMapping("/{id}")
	public RsData<RecruitmentResponse> read(@PathVariable Long id) {
		RsData<Recruitment> rsData = recruitmentService.get(id);
		if (rsData.isFail()) {
			return (RsData)rsData;
		}
		return RsData.of(rsData.getResultCode(), rsData.getMsg(), new RecruitmentResponse(rsData.getData()));
	}
}
