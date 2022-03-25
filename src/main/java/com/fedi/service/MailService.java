package com.fedi.service;

import com.fedi.domain.MailHandler;
import com.fedi.web.dto.MailRequestDto;
import lombok.AllArgsConstructor;
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
            urls += (url+"<br/>");
        }

        String htmlContent = "<p> <img src = 'https://user-images.githubusercontent.com/67725652/148683063-a3119c11-9f92-4728-b7f2-5a6e570e8755.png' height='120' width='600'>\n</p>" +
         		 "<p><br/>안녕하세요, Find your face, Fedi입니다.</p>" +
                "<p>불법합성이 포함된 지인능욕형 범죄는 성폭력처벌법 제14조의2, 제14조의3에 의거하여 디지털 성범죄로 분류되기 때문에 신고와 처벌이 가능합니다. 신고 절차에서 필요한 디지털 성범죄물을 업로드한 것으로 추정되는 원본 트위터 링크를 발송합니다.<br/><br/></p>"+
                "<p><b>원본 트윗 링크</b><br/>" + urls + "<br/></p>" +
                "<p>디지털 성범죄 피해에 대한 접수 등 상담, 삭제지원 및 유포 현황 모니터링, 수사·법률·의료 연계 지원을 제공하는 <a href='https://d4u.stop.or.kr/' target = 'parent'>디지털성범죄피해자신고센터</a>에 해당 링크를 가지고 가해자를 신고할 수 있습니다.<br/></p>" +
                "<p> <img src ='https://user-images.githubusercontent.com/67725652/148682922-490daafa-4a5e-4afb-87fd-ba913481a70a.png' height='463' width='600'> </p>";
        mailHandler.setText(htmlContent, true);

        mailHandler.send();
        return "success";
    }
}
