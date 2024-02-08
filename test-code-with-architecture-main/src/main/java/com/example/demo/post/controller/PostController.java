package com.example.demo.post.controller;

import com.example.demo.post.controller.port.PostReadService;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.controller.UserController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시물(posts)")
@RestController
@Builder
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostReadService postService;
    private final UserController userController;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable long id) {
        return ResponseEntity
                .ok()
                .body(PostResponse.from(postService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable long id, @RequestBody PostUpdate postUpdate) {
        return ResponseEntity
                .ok()
                .body(PostResponse.from(postService.update(id, postUpdate)));
    }
}