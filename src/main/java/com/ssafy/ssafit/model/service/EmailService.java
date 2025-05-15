package com.ssafy.ssafit.model.service;

public interface EmailService {
    String SendCode(String email);
    Boolean checkCode(String email);
}
