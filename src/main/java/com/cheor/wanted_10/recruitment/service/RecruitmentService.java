package com.cheor.wanted_10.recruitment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheor.wanted_10.base.rsData.RsData;
import com.cheor.wanted_10.company.company.CompanyService;
import com.cheor.wanted_10.company.entity.Company;
import com.cheor.wanted_10.recruitment.controller.RecruitmentController;
import com.cheor.wanted_10.recruitment.dto.RecruitmentDTO;
import com.cheor.wanted_10.recruitment.entyty.Recruitment;
import com.cheor.wanted_10.recruitment.repository.RecruitmentRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentService {
	private final CompanyService companyService;
	private final RecruitmentRepository recruitmentRepository;

	@Transactional
	public RsData<Recruitment> create(RecruitmentDTO recruitmentDTO) {
		String companyName = recruitmentDTO.getCompanyName();
		RsData<Company> company = companyService.getCompanyByName(companyName);
		if(company.isFail()) {
			return RsData.of("F-1", "본 서비스에 등록된 회사가 아닙니다.");
		}
		Recruitment recruitment = Recruitment.builder()
			.content(recruitmentDTO.getContent())
			.skill(recruitmentDTO.getSkill())
			.company(company.getData())
			.reward(recruitmentDTO.getReward())
			.position(recruitmentDTO.getPosition())
			.build();

		recruitmentRepository.save(recruitment);

		return RsData.of("S-1", "지원공고가 성공적으로 등록되었습니다.", recruitment);
	}
}
