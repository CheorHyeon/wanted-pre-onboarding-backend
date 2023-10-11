package com.cheor.wanted_10.company.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.cheor.wanted_10.recruitment.entyty.Recruitment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
// JPA Auditing : 시간에 대해 자동으로 값을 넣어주는 기능
@EntityListeners(AuditingEntityListener.class) // + enableJpaAuditing => JPA Auditing 활성
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String nation;

	private String region;
	@OneToMany(mappedBy = "company", cascade = {CascadeType.REMOVE})
	@ToString.Exclude
	private List<Recruitment> recruitmentList = new ArrayList<>();
}
