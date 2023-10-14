package com.cheor.wanted_10.recruitment.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cheor.wanted_10.base.rsData.RsData;
import com.cheor.wanted_10.recruitment.dto.RecruitmentDTO;
import com.cheor.wanted_10.recruitment.dto.RecruitmentModifyDTO;
import com.cheor.wanted_10.recruitment.entyty.Recruitment;
import com.cheor.wanted_10.recruitment.service.RecruitmentService;
import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
		private Long id;
		private String companyName;
		private String position;
		private Integer reward;

		private String content;
		private String skill;

		@JsonCreator
		public RecruitmentResponse(Recruitment recruitment) {
			this.id = recruitment.getId();
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
		private Long id;
		private String companyName;
		private String position;
		private Integer reward;
		private String content;
		private String skill;

		@JsonCreator
		public ModifyResponse(Recruitment recruitment) {
			this.id = recruitment.getId();
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
	public RsData<RecruitmentsResponse> readAll(@RequestParam(required = false) String search) {
		List<Recruitment> recruitments;

		// 혹시 모를 공백 제거
		if (search != null) {
			search = search.trim();
		}

		// 검색어 유무에 따라 다른 메서드 호출
		if (search == null || search.isBlank()) {
			recruitments = recruitmentService.getAll();
		} else {
			recruitments = recruitmentService.getByKeyWord(search);
		}
		return RsData.of(
			"S-1",
			"공고 조회",
			new RecruitmentsResponse(recruitments)
		);
	}

	@AllArgsConstructor
	@Getter
	@NoArgsConstructor
	public static class RecruitmentDetailResponse extends RecruitmentResponse {
		private List<Long> otherRecruitmentsId;

		public RecruitmentDetailResponse(Recruitment recruitment, List<Long> otherRecruitmentsId) {
			super(recruitment);
			this.otherRecruitmentsId = otherRecruitmentsId;
		}
	}

	@GetMapping("/{id}")
	public RsData<RecruitmentDetailResponse> read(@PathVariable Long id) {
		RsData<Recruitment> rsData = recruitmentService.get(id);
		if (rsData.isFail()) {
			return (RsData)rsData;
		}

		Recruitment recruitment = rsData.getData();

		List<Recruitment> recruitments = recruitmentService.getCompanyRecruitments(recruitment.getCompany());

		List<Long> otherRecruitmentsId = new ArrayList<>();
		// 다른 채용공고가 있을 경우만 공고 Id 추가
		if (recruitments.size() > 1) {
			for (Recruitment r : recruitments) {
				otherRecruitmentsId.add(r.getId());
			}
			otherRecruitmentsId.remove(recruitment.getId());
		}
		return RsData.of(rsData.getResultCode(), rsData.getMsg(),
			new RecruitmentDetailResponse(recruitment, otherRecruitmentsId));
	}
}
