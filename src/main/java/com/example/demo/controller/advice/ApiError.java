package com.example.demo.controller.advice;

import java.time.Instant;
import java.util.List;

public record ApiError(
    Instant timestamp,
    Integer status,
    String message,
    List<String> errors
) {}