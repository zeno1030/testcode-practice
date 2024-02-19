package com.example.demo.user.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.user.domain.UserStatus;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

	Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);
}
