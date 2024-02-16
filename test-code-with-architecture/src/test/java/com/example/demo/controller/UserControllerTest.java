package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.infrastructure.UserEntity;
import com.example.demo.user.infrastructure.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@SqlGroup({
	@Sql(value = "/sql/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserJpaRepository userJpaRepository;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void 사용자는_특정_유저의_정보를_전달_받을_수_있다() throws Exception {
		mockMvc.perform(get("/api/users/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.email").value("zeno1030@naver.com"))
			.andExpect(jsonPath("$.nickname").value("zeno1030"));

	}

	@Test
	void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() throws Exception {
		mockMvc.perform(get("/api/users/12341324"))
			.andExpect(status().isNotFound())
			.andExpect(content().string("Users에서 ID 12341324를 찾을 수 없습니다."));

	}

	@Test
	void 사용자는_인증_코드로_계정을_활성화_시킬_수있다() throws Exception {
		mockMvc.perform(get("/api/users/2/verify")
				.queryParam("certificationCode", "aaaaaaaa-aaaa-aaa-aaaa-aaaaaaaaaaaa"))
			.andExpect(status().isFound());
		UserEntity userEntity = userJpaRepository.findById(2L).get();
		Assertions.assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void 사용자는_내_정보를_불러올_때_개인정보인_주소() throws Exception {
		mockMvc.perform(get("/api/users/2/verify")
				.queryParam("certificationCode", "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
			.andExpect(status().isFound());
		UserEntity userEntity = userJpaRepository.findById(2L).get();
		Assertions.assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void 사용자는_내_정보를_수정할_수_있다() throws Exception {
		UserUpdate userUpdate = UserUpdate.builder()
			.nickname("wkrwjs")
			.address("paris")
			.build();
		mockMvc.perform(
				put("/api/users/me")
					.header("EMAIL", "zeno1030@naver.com")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(userUpdate))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.email").value("zeno1030@naver.com"))
			.andExpect(jsonPath("$.nickname").value("wkrwjs"))
			.andExpect(jsonPath("$.address").value("paris"));
	}
}