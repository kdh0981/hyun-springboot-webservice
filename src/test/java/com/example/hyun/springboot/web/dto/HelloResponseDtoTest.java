package com.example.hyun.springboot.web.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloResponseDtoTest {
  @Test
  void lombokTest() {
    int number = 1000;
    String name = "test";
    HelloResponseDto dto = new HelloResponseDto(name, number);
    assertEquals(dto.getName(), name);
    assertEquals(dto.getAmount(), number);
  }
}