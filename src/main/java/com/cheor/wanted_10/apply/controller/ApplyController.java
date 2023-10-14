package com.cheor.wanted_10.apply.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheor.wanted_10.apply.dto.ApplyDTO;
import com.cheor.wanted_10.apply.entity.Apply;
import com.cheor.wanted_10.apply.service.ApplyService;
import com.cheor.wanted_10.base.rsData.RsData;
import com.cheor.wanted_10.recruitment.controller.RecruitmentController;
import com.cheor.wanted_10.recruitment.dto.RecruitmentDTO;
import com.cheor.wanted_10.recruitment.entyty.Recruitment;
import com.cheor.wanted_10.recruitment.service.RecruitmentService;
import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recruitment/apply")
@Tag(name = "ApplyController", description = "사용자 채용 공고 지원")
public class ApplyController {
	private final ApplyService applyService;

	@AllArgsConstructor
	@Getter
	@NoArgsConstructor
	public static class ApplyResponse {
		private Apply apply;
	}
	@PostMapping("")
	@Operation(summary = "사용자 채용 공고 지원 기능")
	public RsData<ApplyResponse> create(@Valid @RequestBody ApplyDTO applyDTO) {

		RsData<Apply> rsData = applyService.create(applyDTO);
		if (rsData.isFail()) {
			return (RsData)rsData;
		}
		return RsData.of(rsData.getResultCode(), rsData.getMsg(), new ApplyResponse(rsData.getData()));
	}

}
