package com.ssafy.ssafit.model.service;

public interface EmailService {
    Boolean sendCode(String email);
    Boolean checkCode(String email, String codeInput);
}
