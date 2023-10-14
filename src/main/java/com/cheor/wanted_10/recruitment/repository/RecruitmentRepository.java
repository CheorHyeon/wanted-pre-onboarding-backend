package com.cheor.wanted_10.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cheor.wanted_10.company.entity.Company;
import com.cheor.wanted_10.recruitment.entyty.Recruitment;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
	List<Recruitment> findAllByOrderByIdDesc();

	List<Recruitment> findAllByCompany(Company company);

	@Query("select "
		+ "distinct r "
		+ "from Recruitment r "
		+ "where "
		+ "   r.company.name like %:keyWord% "
		+ "   or r.content like %:keyWord% "
		+ "   or r.position like %:keyWord% "
		+ "   or CAST(r.reward AS string) like %:keyWord% "
		+ "   or r.skill like %:keyWord% ")
	List<Recruitment> findAllByKeyWord(@Param("keyWord") String keyWord);
}
