package com.demo.concert.dto.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
  private int code;
  private String message;
  private Object data;
}
