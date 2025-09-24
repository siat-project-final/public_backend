package com.takoyakki.backend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorsResponse {
    private int status;
    private String errorTitle;
    private List<String> errors;
}
