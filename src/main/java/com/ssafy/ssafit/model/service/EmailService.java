package com.ssafy.ssafit.model.service;

public interface EmailService {
    Boolean SendCode(String email);
    Boolean code(String email, String codeInput);
}
