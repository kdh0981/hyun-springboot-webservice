package com.example.hyun.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class PostsRepositoryTest {
  @Autowired
  PostsRepository postsRepository;

  @AfterEach
  public void cleanup() {
    postsRepository.deleteAll();
  }

  @Test
  public void saveAndFindAll() {
    String title = "제목";
    String content = "본문";

    postsRepository.save(Posts.builder()
      .title(title)
      .content(content)
      .author("kdh0981")
      .build());

    List<Posts> postsList = postsRepository.findAll();

    Posts posts = postsList.get(0);
    assertEquals(posts.getTitle(), title);
    assertEquals(posts.getContent(), content);
  }
}