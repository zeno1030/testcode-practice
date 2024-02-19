package com.example.demo.post.service;

import org.springframework.stereotype.Service;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.post.service.port.PostRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserService userService;
	private final ClockHolder clockHolder;

	public Post getPostById(long id) {
		return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
	}

	public Post create(PostCreate postCreate) {
		User user = userService.getById(postCreate.getWriterId());
		Post post = Post.from(user, postCreate, clockHolder);

		return postRepository.save(post);
	}

	public Post update(long id, PostUpdate postUpdate) {
		Post post = getPostById(id);
		return postRepository.save(post);
	}
}