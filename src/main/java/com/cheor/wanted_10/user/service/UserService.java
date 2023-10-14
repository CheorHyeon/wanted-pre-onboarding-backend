package com.cheor.wanted_10.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheor.wanted_10.base.rsData.RsData;
import com.cheor.wanted_10.user.entity.SiteUser;
import com.cheor.wanted_10.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;
	public RsData<SiteUser> get(Long siteUserId) {
		Optional<SiteUser> opSiteUser = userRepository.findById(siteUserId);
		if(opSiteUser.isEmpty()) {
			return RsData.of("F-1", "존재하지 않는 사용자 입니다.");
		}
		return RsData.of("S-1", "사용자 조회 성공", opSiteUser.get());
	}
}
