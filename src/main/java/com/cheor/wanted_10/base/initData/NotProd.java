package com.cheor.wanted_10.base.initData;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import com.cheor.wanted_10.apply.repository.ApplyRepository;
import com.cheor.wanted_10.company.entity.Company;
import com.cheor.wanted_10.company.repository.CompanyRepository;
import com.cheor.wanted_10.recruitment.entyty.Recruitment;
import com.cheor.wanted_10.recruitment.repository.RecruitmentRepository;
import com.cheor.wanted_10.user.entity.SiteUser;
import com.cheor.wanted_10.user.repository.UserRepository;

@Configuration
@Profile({"dev", "test"})
public class NotProd {
	@Bean
	 CommandLineRunner initData(
		 CompanyRepository companyRepository,
		RecruitmentRepository recruitmentRepository,
		UserRepository userRepository,
		ApplyRepository applyRepository
	) {
		return new CommandLineRunner() {
			@Override
			@Transactional
			public void run(String... args) throws Exception {

				Company company1 = Company.builder()
					.name("회사1")
					.region("서울")
					.nation("한국")
					.build();

				Company company2 = Company.builder()
					.name("회사2")
					.region("서울")
					.nation("한국")
					.build();

				companyRepository.saveAll(List.of(company1, company2));

				SiteUser user1 = SiteUser.builder()
					.userId("puar12")
					.build();

				SiteUser user2 = SiteUser.builder()
					.userId("wanted123")
					.build();

				userRepository.saveAll(List.of(user1, user2));

				Recruitment recruitment1 = Recruitment.builder()
					.content("테스트 채용1")
					.skill("python")
					.reward(3000)
					.company(company1)
					.position("백엔드")
					.build();

				Recruitment recruitment2 = Recruitment.builder()
					.content("테스트 채용2")
					.skill("java")
					.reward(1000)
					.company(company2)
					.position("백엔드")
					.build();

				recruitmentRepository.saveAll(List.of(recruitment1, recruitment2));
			}
		};
	}
}
