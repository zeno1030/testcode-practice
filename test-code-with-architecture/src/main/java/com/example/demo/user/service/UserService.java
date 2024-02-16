package com.example.demo.user.service;

import java.time.Clock;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.infrastructure.UserEntity;
import com.example.demo.user.service.port.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final CertificationService certificationService;

	public UserEntity getByEmail(String email) {
		return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
			.orElseThrow(() -> new ResourceNotFoundException("Users", email));
	}

	public UserEntity getById(long id) {
		return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
			.orElseThrow(() -> new ResourceNotFoundException("Users", id));
	}

	@Transactional
	public UserEntity create(UserCreate userCreateDto) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(userCreateDto.getEmail());
		userEntity.setNickname(userCreateDto.getNickname());
		userEntity.setAddress(userCreateDto.getAddress());
		userEntity.setStatus(UserStatus.PENDING);
		userEntity.setCertificationCode(UUID.randomUUID().toString());
		userEntity = userRepository.save(userEntity);
		certificationService.send(userEntity.getEmail(), userEntity.getId(), userEntity.getCertificationCode());
		return userEntity;
	}

	@Transactional
	public UserEntity update(long id, UserUpdate userUpdateDto) {
		UserEntity userEntity = getById(id);
		userEntity.setNickname(userUpdateDto.getNickname());
		userEntity.setAddress(userUpdateDto.getAddress());
		userEntity = userRepository.save(userEntity);
		return userEntity;
	}

	@Transactional
	public void login(long id) {
		UserEntity userEntity = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Users", id));
		userEntity.setLastLoginAt(Clock.systemUTC().millis());
	}

	@Transactional
	public void verifyEmail(long id, String certificationCode) {
		UserEntity userEntity = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Users", id));
		if (!certificationCode.equals(userEntity.getCertificationCode())) {
			throw new CertificationCodeNotMatchedException();
		}
		userEntity.setStatus(UserStatus.ACTIVE);
	}

}