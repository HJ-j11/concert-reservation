package com.demo.concert.dto.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
  private int code;
  private String message;
  private T data;
}
