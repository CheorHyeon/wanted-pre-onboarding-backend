package com.cheor.wanted_10.apply.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDTO {
	@NotNull(message = "등록된 채용공고의 id를 입력해주세요")
	private Long recruitmentId;

	@NotNull(message = "사용자 id를 입력해주세요")
	private Long siteUserId;
}