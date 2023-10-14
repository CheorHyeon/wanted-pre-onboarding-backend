package com.cheor.wanted_10.recruitment.entyty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.cheor.wanted_10.company.entity.Company;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
// JPA Auditing : 시간에 대해 자동으로 값을 넣어주는 기능
@EntityListeners(AuditingEntityListener.class) // + enableJpaAuditing => JPA Auditing 활성
public class Recruitment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Company company;

	private String position;

	private Integer reward;

	private String content;

	private String skill;

	@CreatedDate
	private LocalDateTime regDate;

	@LastModifiedDate
	private LocalDateTime modifyDate;

	@PrePersist
	public void prePersist() {
		this.modifyDate = null; // 객체 생성 시 처음에 수정일 null값으로 설정
	}

}