package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.EmailDao;
import com.ssafy.ssafit.model.dto.Email;
import lombok.*;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    // 생성 및 메일 전송
    @Override
    public Boolean sendCode(String address) {
        Email exist = emailDao.select(address);
        if(exist != null){
            emailDao.delete(exist.getEmailId());
        }
        // 테이블에 임시 코드 넣기
        String code = createCode();
        Email email = new Email();
        email.setAddress(address);
        email.setToken(code);
        email.setDue(LocalDateTime.now().plusMinutes(5)); // 5분
        emailDao.insert(email);
        // 이메일 전송하기
        try{
            SimpleMailMessage smm = new SimpleMailMessage();
            smm.setTo(address);
            smm.setFrom("SSAFIT");
            smm.setSubject("[SSAFIT] 본인 인증 문자입니다.");
            smm.setText("SSAFIT 본인 인증 문자입니다. 최대 5분간 유효합니다.\n"+code);
            javaMailSender.send(smm);
        } catch (MailSendException e){
            e.printStackTrace();
            emailDao.delete(email.getEmailId());
            return false;
        }
        return true;
    }


    // 체크
    @Override
    public Boolean checkCode(String address, String codeInput) {
        Email email = emailDao.select(address);
        if(email == null){
            return false;
        }
        if(email.getDue().isAfter(LocalDateTime.now())){
            return false;
        }
        return email.getToken().equalsIgnoreCase(codeInput);
    }
}
