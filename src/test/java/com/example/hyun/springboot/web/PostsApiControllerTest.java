package com.example.hyun.springboot.web;

import com.example.hyun.springboot.domain.posts.Posts;
import com.example.hyun.springboot.domain.posts.PostsRepository;
import com.example.hyun.springboot.web.dto.PostsRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PostsRepository postsRepository;

  @AfterEach
  void tearDown() throws Exception {
    postsRepository.deleteAll();
  }

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders
      .webAppContextSetup(context)
      .apply(SecurityMockMvcConfigurers.springSecurity())
      .build();
  }


  @Test
  @WithMockUser(roles="USER")
  void registerPosts() throws Exception {
    String title = "title";
    String content = "content";
    PostsRequestDto requestDto = PostsRequestDto.builder()
      .title(title)
      .content(content)
      .author("kdh0981")
      .build();

    String url = "http://localhost:" + port + "/api/v1/posts";

//    HttpEntity<PostsRequestDto> entity = new HttpEntity<>(requestDto);
//    ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, Long.class);
//
//    assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//    assertTrue(responseEntity.getBody() > 0L);

    //when
    mvc.perform(MockMvcRequestBuilders.post(url)
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(requestDto)))
      .andExpect(MockMvcResultMatchers.status().isOk());

    //then
    List<Posts> all = postsRepository.findAll();
    assertEquals(all.get(0).getTitle(), title);
    assertEquals(all.get(0).getContent(), content);
  }

  @Test
  @WithMockUser(roles="USER")
  void updatePosts() throws Exception {
    Posts savedPosts = postsRepository.save(Posts.builder()
      .title("title")
      .content("content")
      .author("author")
      .build());

    Long updateId = savedPosts.getId();
    String expectedTitle = "title2";
    String expectedContent = "content2";

    PostsRequestDto requestDto = PostsRequestDto.builder()
      .title(expectedTitle)
      .content(expectedContent)
      .build();

    String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

//    HttpEntity<PostsRequestDto> requestEntity = new HttpEntity<>(requestDto);
//    ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
//
//    assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//    assertEquals(responseEntity.getBody(), updateId);

    // when
    mvc.perform(MockMvcRequestBuilders.put(url)
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(requestDto))
    ).andExpect(MockMvcResultMatchers.status().isOk());

    // then
    List<Posts> all = postsRepository.findAll();
    assertEquals(all.get(0).getTitle(), expectedTitle);
    assertEquals(all.get(0).getContent(), expectedContent);
  }

  @Test
  void baseTimeEntitiyTest() {
    LocalDateTime now = LocalDateTime.of(2019, 12, 27, 0, 0, 0);
    postsRepository.save(Posts.builder()
      .title("title")
      .content("content")
      .author("author")
      .build());

    List<Posts> postsList = postsRepository.findAll();
    Posts posts = postsList.get(0);

    System.out.println(">>>>>> createDate=" + posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());
    assertTrue(posts.getCreatedDate().isAfter(now));
    assertTrue(posts.getModifiedDate().isAfter(now));
  }
}