package com.example.hyun.springboot.web.dto;

import com.example.hyun.springboot.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsResponseDto {
  private Long id;
  private String title;
  private String content;
  private String author;
  private LocalDateTime modifiedDate;

  public PostsResponseDto(Posts entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.author = entity.getAuthor();
    this.modifiedDate = entity.getModifiedDate();
  }
}
