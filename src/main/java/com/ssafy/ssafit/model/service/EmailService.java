package com.ssafy.ssafit.model.service;

public interface EmailService {
    String createCode(String email);
    Boolean sendCode(String code);
    Boolean checkCode(String email);
}
