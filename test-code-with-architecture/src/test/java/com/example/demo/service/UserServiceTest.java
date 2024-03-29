package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.infrastructure.User;
import com.example.demo.user.service.UserService;

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
		com.example.demo.user.domain.User result = userService.getByEmail(email);
		//then
		assertThat(result.getId()).isNotNull();
	}

	@Test
	void getByEmail은_PENDING_상태인_유저를_찾아올_수없다() {
		//given
		String email = "wl3648ghks@gmail.com";

		assertThatThrownBy(() -> {
			User result = User.fromModel(userService.getByEmail(email));
		}).isInstanceOf(ResourceNotFoundException.class);

	}

	@Test
	void getById는_ACTIVE_상태인_유저를_찾아올_수있다() {
		User result = User.fromModel(userService.getById(1));

		assertThat(result.getNickname()).isEqualTo("zeno1030");

	}

	@Test
	void getById는_PENDING_상태인_유저를_찾아올_수없다() {
		assertThatThrownBy(() -> {
			User result = User.fromModel(userService.getById(2));
		}).isInstanceOf(ResourceNotFoundException.class);

	}

	@Test
	void create는_email을_사용해서_계정을_만들_수있다() {
		//given
		UserCreate userCreate = UserCreate.builder()
			.email("zeno1030@naver.com")
			.address("Gyeongi")
			.nickname("wl3648ghks")
			.build();

		BDDMockito.doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

		//when
		User result = User.fromModel(userService.create(userCreate));

		assertThat(result.getId()).isNotNull();
		assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
	}

	@Test
	void userUpdateDto를_이요하여_유저를_수정할_수_있다() {
		//given
		UserUpdate userUpdate = UserUpdate.builder()
			.address("seoul")
			.nickname("wkrwjs")
			.build();
		//when
		User result = User.fromModel(userService.update(1, userUpdate));

		assertThat(result.getAddress()).isEqualTo("seoul");
		assertThat(result.getNickname()).isEqualTo("wkrwjs");
	}

	@Test
	void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
		//when
		userService.login(1);

		User user = User.fromModel(userService.getById(1));
		log.info("getLastLoginAt = {}", user.getLastLoginAt());
		assertThat(user.getLastLoginAt()).isGreaterThan(0L);
	}

	@Test
	void Pending_상태의_사용자는_인증_코드로_ACTIVE_시킬_수있다() {
		userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

		User user = User.fromModel(userService.getById(2));
		assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}
}