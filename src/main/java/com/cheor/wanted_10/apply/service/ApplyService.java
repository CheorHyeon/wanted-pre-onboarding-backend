package com.cheor.wanted_10.apply.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheor.wanted_10.apply.controller.ApplyController;
import com.cheor.wanted_10.apply.dto.ApplyDTO;
import com.cheor.wanted_10.apply.entity.Apply;
import com.cheor.wanted_10.apply.repository.ApplyRepository;
import com.cheor.wanted_10.base.rsData.RsData;
import com.cheor.wanted_10.company.entity.Company;
import com.cheor.wanted_10.recruitment.entyty.Recruitment;
import com.cheor.wanted_10.recruitment.service.RecruitmentService;
import com.cheor.wanted_10.user.entity.SiteUser;
import com.cheor.wanted_10.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyService {
	private final RecruitmentService recruitmentService;
	private final UserService userService;
	private final ApplyRepository applyRepository;
	@Transactional
	public RsData<Apply> create(ApplyDTO applyDTO) {
		// 공고 존재하는지 확인
		RsData<Recruitment> recruitmentRsData = recruitmentService.get(applyDTO.getRecruitmentId());
		if(recruitmentRsData.isFail()) {
			return RsData.of("F-1", "존재하지 않는 공고입니다.");
		}
		// 사용자 존재하는지 확인
		RsData<SiteUser> siteUserRsData = userService.get(applyDTO.getSiteUserId());
		if(siteUserRsData.isFail()) {
			return RsData.of("F-1", "존재하지 않는 사용자입니다.");
		}
		// 이전에 지원했었는지 확인
		if(havingApplyHistory(applyDTO.getRecruitmentId(), applyDTO.getSiteUserId())){
			return RsData.of("F-1", "이미 지원하였습니다.");
		}
		// 누가, 어떤회사의, 어떤 공고에 지원했다는 정보를 Apply에 저장하기 위한 정보 추출
		Recruitment recruitment = recruitmentService.get(applyDTO.getRecruitmentId()).getData();
		Company company = recruitment.getCompany();
		SiteUser user = userService.get(applyDTO.getSiteUserId()).getData();

		Apply history = Apply.builder()
			.company(company)
			.siteUser(user)
			.recruitment(recruitment)
			.build();

		applyRepository.save(history);

		return RsData.of("S-1", "성공적으로 지원하였습니다.", history);

	}

	private boolean havingApplyHistory(Long recruitmentId, Long siteUserId) {
		Apply applyHistory = applyRepository.findBySiteUserIdAndRecruitmentId(siteUserId, recruitmentId);

		if(applyHistory ==null) {
			return false;
		}
		return true;
	}
}
