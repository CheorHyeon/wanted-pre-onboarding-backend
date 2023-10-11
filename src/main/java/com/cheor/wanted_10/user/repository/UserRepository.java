package com.cheor.wanted_10.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheor.wanted_10.user.entity.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
}
