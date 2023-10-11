package com.cheor.wanted_10.apply.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.cheor.wanted_10.company.entity.Company;
import com.cheor.wanted_10.recruitment.entyty.Recruitment;
import com.cheor.wanted_10.user.entity.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
// JPA Auditing : 시간에 대해 자동으로 값을 넣어주는 기능
@EntityListeners(AuditingEntityListener.class) // + enableJpaAuditing => JPA Auditing 활성
public class Apply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private SiteUser siteUser;
	@OneToOne
	private Company company;
	@OneToOne
	private Recruitment recruitment;
	@CreatedDate
	private LocalDateTime regDate;

	@LastModifiedDate
	private LocalDateTime modifyDate;

	@PrePersist
	public void prePersist() {
		this.modifyDate = null; // 객체 생성 시 처음에 수정일 null값으로 설정
	}
}
