package com.cheor.wanted_10.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheor.wanted_10.recruitment.entyty.Recruitment;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
}
