package com.cheor.wanted_10.recruitment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.cheor.wanted_10.recruitment.entyty.Recruitment;
import com.cheor.wanted_10.recruitment.service.RecruitmentService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.MethodName.class)
public class RecruitmentControllerTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private RecruitmentService recruitmentService;

	@Test
	@DisplayName("채용공고 등록 테스트")
	void t001() throws Exception {
		ResultActions resultActions = mvc
			.perform(
				post("/recruitment/register")
					.content("""
						{
							"companyName": "회사1",
						   "position": "백엔드 주니어 개발자",
						   "reward": 1000000,
						   "content": "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
						   "skill":"Python"
						}
						""")
					.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
			)
			.andDo(print());

		resultActions
			.andExpect(status().is2xxSuccessful())
			.andExpect(handler().methodName("register"))
			.andExpect(jsonPath("$.resultCode").value("S-1"))
			.andExpect(jsonPath("$.data.skill").value("Python"));
	}

	@Test
	@DisplayName("채용공고 수정 테스트1")
	void t002() throws Exception {
		ResultActions resultActions = mvc
			.perform(
				patch("/recruitment/modify/1")
					.content("""
						{
						   "position": "수정된개발자공고!"
						}
						""")
					.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
			)
			.andDo(print());

		resultActions
			.andExpect(status().is2xxSuccessful())
			.andExpect(handler().methodName("modify"))
			.andExpect(jsonPath("$.resultCode").value("S-1"))
			.andExpect(jsonPath("$.data.position").value("수정된개발자공고!"));
	}

	@Test
	@DisplayName("채용공고 수정 테스트2")
	void t003() throws Exception {
		ResultActions resultActions = mvc
			.perform(
				patch("/recruitment/modify/1")
					.content("""
						{
						   "skill": "수정된skill!"
						}
						""")
					.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
			)
			.andDo(print());

		resultActions
			.andExpect(status().is2xxSuccessful())
			.andExpect(handler().methodName("modify"))
			.andExpect(jsonPath("$.resultCode").value("S-1"))
			.andExpect(jsonPath("$.data.skill").value("수정된skill!"));
	}
}
