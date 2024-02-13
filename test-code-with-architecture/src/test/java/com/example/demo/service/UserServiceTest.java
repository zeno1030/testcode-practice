package com.example.demo.service;


import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;


import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.UserStatus;
import com.example.demo.model.dto.UserCreateDto;
import com.example.demo.model.dto.UserUpdateDto;
import com.example.demo.repository.UserEntity;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
	@Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@Slf4j
class UserServiceTest {
	@Autowired
	private UserService userService;
	@MockBean
	private JavaMailSender javaMailSender;

	@Test
	void getByEmail은_ACTIVE_상태인_유저를_찾아올_수있다() {
		//given
		String email = "zeno1030@naver.com";

		//when
		UserEntity result = userService.getByEmail(email);
		//then
		assertThat(result.getId()).isNotNull();
	}
	@Test
	void getByEmail은_PENDING_상태인_유저를_찾아올_수없다() {
		//given
		String email = "wl3648ghks@gmail.com";

		assertThatThrownBy(() -> {
			UserEntity result = userService.getByEmail(email);
		}).isInstanceOf(ResourceNotFoundException.class);

	}
	@Test
	void getById는_ACTIVE_상태인_유저를_찾아올_수있다() {
		UserEntity result = userService.getById(1);

		assertThat(result.getNickname()).isEqualTo("zeno1030");

	}

	@Test
	void getById는_PENDING_상태인_유저를_찾아올_수없다() {
		assertThatThrownBy(() -> {
			UserEntity result = userService.getById(2);
		}).isInstanceOf(ResourceNotFoundException.class);

	}

	@Test
	void create는_email을_사용해서_계정을_만들_수있다() {
		//given
		UserCreateDto userCreateDto = UserCreateDto.builder()
			.email("zeno1030@naver.com")
			.address("Gyeongi")
			.nickname("wl3648ghks")
			.build();

		BDDMockito.doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

		//when
		UserEntity result = userService.create(userCreateDto);

		assertThat(result.getId()).isNotNull();
		assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
	}
	@Test
	void userUpdateDto를_이요하여_유저를_수정할_수_있다() {
		//given
		UserUpdateDto userUpdateDto = UserUpdateDto.builder()
			.address("seoul")
			.nickname("wkrwjs")
			.build();
		//when
		UserEntity result = userService.update(1, userUpdateDto);

		assertThat(result.getAddress()).isEqualTo("seoul");
		assertThat(result.getNickname()).isEqualTo("wkrwjs");
	}
	@Test
	void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
		//when
		userService.login(1);

		UserEntity userEntity = userService.getById(1);
		log.info("getLastLoginAt = {}", userEntity.getLastLoginAt());
		assertThat(userEntity.getLastLoginAt()).isGreaterThan(0L);
	}

	@Test
	void Pending_상태의_사용자는_인증_코드로_ACTIVE_시킬_수있다() {
		userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

		UserEntity userEntity = userService.getById(2);
		assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}
}