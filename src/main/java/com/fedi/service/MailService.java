package com.fedi.service;

import com.fedi.domain.MailHandler;
import com.fedi.web.dto.MailRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

@AllArgsConstructor
@Service
public class MailService {
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "feditection@gmail.com";

    @Transactional
    public String sendMail(MailRequestDto requestDto) throws MessagingException {
        MailHandler mailHandler = new MailHandler(mailSender);

        mailHandler.setFrom(FROM_ADDRESS);
        mailHandler.setTo(requestDto.getRecipient());
        mailHandler.setSubject("Fedi 증거 수집 자료 이메일입니다.");

        String urls = "";
        for(String url : requestDto.getTweetUrls()){
            urls += (url+"\n\n");
        }

        String htmlContent = "<p> <img src = 'https://user-images.githubusercontent.com/67725652/148683063-a3119c11-9f92-4728-b7f2-5a6e570e8755.png' height='236' width='1041'>\n</p>" +
                "<p>안녕하세요, Find your face, Fedi입니다. 디지털성범죄물을 업로드한 것으로 추정되는 원본 트위터 링크를 발송합니다.\n</p>" +
                "<p>" + urls + "\n</p>" +
                "<p>디지털성범죄피해자신고센터에 해당 링크를 가지고 가해자를 신고할 수 있습니다.\n</p>" +
                "<p> <img src ='https://user-images.githubusercontent.com/67725652/148682922-490daafa-4a5e-4afb-87fd-ba913481a70a.png' height='463' width='600'> </p>";
        mailHandler.setText(htmlContent, true);

        mailHandler.send();
        return "success";
    }
}
