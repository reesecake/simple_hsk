package com.reesecake.simple_hsk.vocab;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class VocabNotFoundAdvice {

    @ResponseBody  // advice goes straight into response body
    @ExceptionHandler(VocabNotFoundException.class)  // advice only responds if VocabNotFoundException is thrown
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String vocabNotFoundHandler(VocabNotFoundException ex) {
        return ex.getMessage();
    }
}
