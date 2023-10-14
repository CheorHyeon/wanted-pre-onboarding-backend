package com.cheor.wanted_10.company.service;

import org.springframework.stereotype.Service;

import com.cheor.wanted_10.base.rsData.RsData;
import com.cheor.wanted_10.company.entity.Company;
import com.cheor.wanted_10.company.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
	private final CompanyRepository companyRepository;

	public RsData<Company> getCompanyByName(String companyName) {
		Company findCompany = companyRepository.findByName(companyName);
		if (findCompany == null) {
			return RsData.of("F-1", "본 서비스에 등록된 회사가 아닙니다.");
		}

		return RsData.of("S-1", "회사 찾기 성공", findCompany);
	}
}
