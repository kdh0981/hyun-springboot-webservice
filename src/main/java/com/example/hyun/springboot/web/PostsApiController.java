package com.example.hyun.springboot.web;

import com.example.hyun.springboot.service.posts.PostsService;
import com.example.hyun.springboot.web.dto.PostsRequestDto;
import com.example.hyun.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
  private final PostsService postsService;

  @PostMapping("/api/v1/posts")
  public Long save(@RequestBody PostsRequestDto requestDto) {
    return postsService.save(requestDto);
  }

  @PutMapping("/api/v1/posts/{id}")
  public Long update(@PathVariable Long id, @RequestBody PostsRequestDto requestDto) {
    return postsService.update(id, requestDto);
  }

  @DeleteMapping("/api/v1/posts/{id}")
  public Long delete(@PathVariable Long id) {
    postsService.delete(id);
    return id;
  }

  @GetMapping("/api/v1/posts/{id}")
  public PostsResponseDto findById(@PathVariable Long id) {
    return postsService.findById(id);
  }
}
