package com.cheor.wanted_10.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheor.wanted_10.company.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
