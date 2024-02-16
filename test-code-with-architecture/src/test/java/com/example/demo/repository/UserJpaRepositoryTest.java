package com.example.demo.repository;


import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.infrastructure.UserEntity;
import com.example.demo.user.infrastructure.UserJpaRepository;

@DataJpaTest(showSql = true)
@TestPropertySource("classpath:test-application.properties")
@Sql("/sql/user-repository-test-data.sql")
class UserJpaRepositoryTest {
	@Autowired
	private UserJpaRepository userJpaRepository;
	@Test
	void findByIdAndStatus를_사용해서_userData를_찾을_수_있다() {
		Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1, UserStatus.ACTIVE);
		//then
		assertThat(result.isPresent()).isTrue();

	}
	@Test
	void findByIdAndStatus는_데이터가_없으면_Optional_empty를_내려준다() {
		Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1, UserStatus.PENDING);
		//then
		assertThat(result.isEmpty()).isTrue();

	}

	@Test
	void findByEmailAndStatus를_사용해서_조회() {
		Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("zeno1030@naver.com", UserStatus.ACTIVE);
		//then
		assertThat(result.isEmpty()).isFalse();

	}

}
