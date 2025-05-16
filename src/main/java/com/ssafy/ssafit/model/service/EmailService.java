package com.ssafy.ssafit.model.service;

public interface EmailService {
    boolean sendCode(String email);
    boolean checkCode(String email, String codeInput);
    boolean isVerifiedEmail(String address);
}
