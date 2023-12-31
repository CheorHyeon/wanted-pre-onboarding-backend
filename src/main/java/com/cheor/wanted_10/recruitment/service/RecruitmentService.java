package com.cheor.wanted_10.recruitment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheor.wanted_10.base.rsData.RsData;
import com.cheor.wanted_10.company.entity.Company;
import com.cheor.wanted_10.company.service.CompanyService;
import com.cheor.wanted_10.recruitment.dto.RecruitmentDTO;
import com.cheor.wanted_10.recruitment.dto.RecruitmentModifyDTO;
import com.cheor.wanted_10.recruitment.entyty.Recruitment;
import com.cheor.wanted_10.recruitment.repository.RecruitmentRepository;

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
		if (company.isFail()) {
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

		return RsData.of("S-2", "지원공고가 성공적으로 등록되었습니다.", recruitment);
	}

	@Transactional
	public RsData<Recruitment> modify(Long recruitmentId, RecruitmentModifyDTO recruitmentModifyDTO) {
		Optional<Recruitment> opRecruitment = recruitmentRepository.findById(recruitmentId);

		if (opRecruitment.isEmpty()) {
			return RsData.of("F-2", "등록되지 않은 공고입니다.");
		}

		Recruitment recruitment = opRecruitment.get();

		Recruitment modifyRecruitment = recruitment.toBuilder()
			.position(recruitmentModifyDTO.getPosition() == null ? recruitment.getPosition() :
				recruitmentModifyDTO.getPosition())
			.content(recruitmentModifyDTO.getContent() == null ? recruitment.getContent() :
				recruitmentModifyDTO.getContent())
			.reward(
				recruitmentModifyDTO.getReward() == null ? recruitment.getReward() : recruitmentModifyDTO.getReward())
			.skill(recruitmentModifyDTO.getSkill() == null ? recruitment.getSkill() : recruitmentModifyDTO.getSkill())
			.build();

		recruitmentRepository.save(modifyRecruitment);

		return RsData.of("S-3", "공고가 수정되었습니다.", modifyRecruitment);
	}

	@Transactional
	public RsData delete(Long id) {
		Optional<Recruitment> opRecruitment = recruitmentRepository.findById(id);

		if (opRecruitment.isEmpty()) {
			return RsData.of("F-2", "존재하지 않는 공고입니다.");
		}

		Recruitment recruitment = opRecruitment.get();

		recruitmentRepository.delete(recruitment);

		return RsData.of("S-4",
			"%s 회사의 %s 직무 공고가 성공적으로 삭제되었습니다.".formatted(recruitment.getCompany().getName(), recruitment.getPosition()));
	}

	public List<Recruitment> getAll() {
		return recruitmentRepository.findAllByOrderByIdDesc();
	}

	public RsData<Recruitment> get(Long id) {
		Optional<Recruitment> opRecruitment = recruitmentRepository.findById(id);

		if (opRecruitment.isEmpty()) {
			return RsData.of("F-2", "존재하지 않는 공고입니다.");
		}

		return RsData.of("S-5", "공고 조회 성공", opRecruitment.get());
	}

	public List<Recruitment> getCompanyRecruitments(Company company) {
		return recruitmentRepository.findAllByCompany(company);
	}

	public List<Recruitment> getByKeyWord(String keyWord) {
		return recruitmentRepository.findAllByKeyWord(keyWord);
	}
}
