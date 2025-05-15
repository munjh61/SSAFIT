package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.EmailDao;
import com.ssafy.ssafit.model.dto.Emailtmp;
import lombok.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;
    private final EmailDao emailDao;
    private List<String> letters = Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789".split(""));

    public String createCode(){
        Collections.shuffle(letters);
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < 6 ; i++){
            sb.append(letters.get(i));
        }
        return sb.toString();
    }

    @Override
    public String SendCode(String email) {
        String code = createCode();
        Emailtmp emailtmp = emailDao.select(email);
        if(emailtmp != null){
            emailDao.delete(emailtmp.getEmailtmpId());
        }
        return "";
    }

    @Override
    public Boolean checkCode(String email) {
        return null;
    }
}
