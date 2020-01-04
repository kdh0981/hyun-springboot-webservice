package com.example.hyun.springboot.service.posts;

import com.example.hyun.springboot.domain.posts.Posts;
import com.example.hyun.springboot.domain.posts.PostsRepository;
import com.example.hyun.springboot.web.dto.PostsRequestDto;
import com.example.hyun.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
  final PostsRepository postsRepository;

  @Transactional
  public Long save(PostsRequestDto requestDto) {
    return postsRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional
  public Long update(Long id, PostsRequestDto requestDto) {
    Posts posts = postsRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

    posts.update(requestDto.getTitle(), requestDto.getContent());
    return id;
  }

  @Transactional
  public void delete(Long id) {
    Posts posts = postsRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

    postsRepository.delete(posts);
  }

  @Transactional(readOnly = true)
  public PostsResponseDto findById(Long id) {
    Posts entity = postsRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
    return new PostsResponseDto(entity);
  }

  @Transactional(readOnly = true)
  public List<PostsResponseDto> findAllDesc() {
    return postsRepository.findAllDesc()
      .stream()
      .map(PostsResponseDto::new)
      .collect(Collectors.toList());
  }
}
