package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.EmailDao;
import com.ssafy.ssafit.model.dao.UserDao;
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
    private final UserDao userDao;
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
    public boolean sendCode(String address) {
        Email exist = emailDao.select(address);
        if(exist != null||exist.getDue().isBefore(LocalDateTime.now())){
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


    // 인증되도록 체크하고 해당 인증의 유효기간을 설정함
    @Override
    public boolean checkCode(String address, String codeInput) {
        Email email = emailDao.select(address);
        if(email == null){
            return false;
        }
        if(email.getDue().isBefore(LocalDateTime.now())){
            return false;
        }
        if(!email.getToken().equalsIgnoreCase(codeInput)){
            return false;
        }
        email.setVerified(true);
        email.setDue(LocalDateTime.now().plusMinutes(5)); // 인증 후 5분
        emailDao.update(email);
        return true;
    }

    // 이미 인증이 된 이메일을 가지고 뭔가 하고 싶을 때
    public boolean isVerifiedEmail(String address){
        Email email = emailDao.select(address);
        if(email == null){
            return false;
        }
        if(!email.isVerified()){
            return false;
        }
        if(email.getDue().isBefore(LocalDateTime.now().minusMinutes(5))){
            return false;
        }
        return true;
    }

    public String verifiedUserId(String address){
        if(!isVerifiedEmail(address)){
            return null;
        }
        return userDao.selectByEmail(address).getUserId();
    }

}
