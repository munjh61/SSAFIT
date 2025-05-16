package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.EmailDao;
import com.ssafy.ssafit.model.dto.Emailtmp;
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
    public Boolean SendCode(String email) {
        Emailtmp exist = emailDao.select(email);
        if(exist != null){
            emailDao.delete(exist.getEmailtmpId());
        }
        // 테이블에 임시 코드 넣기
        String code = createCode();
        Emailtmp emailtmp = new Emailtmp();
        emailtmp.setEmail(email);
        emailtmp.setToken(code);
        emailtmp.setDue(LocalDateTime.now().plusMinutes(5)); // 5분
        emailDao.insert(emailtmp);
        // 이메일 전송하기
        try{
            SimpleMailMessage smm = new SimpleMailMessage();
            smm.setTo(email);
            smm.setFrom("SSAFIT");
            smm.setSubject("[SSAFIT] 본인 인증 문자입니다.");
            smm.setText("SSAFIT 본인 인증 문자입니다. 최대 5분간 유효합니다.\n"+code);
            javaMailSender.send(smm);
        } catch (MailSendException e){
            emailDao.delete(emailtmp.getEmailtmpId());
            return false;
        }
        return true;
    }


    // 체크
    @Override
    public Boolean code(String email, String codeInput) {
        Emailtmp emailtmp = emailDao.select(email);
        if(emailtmp == null){
            return false;
        }
        if(emailtmp.getDue().isAfter(LocalDateTime.now())){
            return false;
        }
        return emailtmp.getToken().equals(codeInput);
    }
}
