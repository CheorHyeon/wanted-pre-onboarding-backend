package com.cheor.wanted_10.apply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheor.wanted_10.apply.entity.Apply;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
	Apply findBySiteUserIdAndRecruitmentId(Long userId, Long recruitmentId);
}
