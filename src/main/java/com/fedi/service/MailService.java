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
        mailHandler.setSubject("Fedi: evidence collection email.");

        String urls = "";
        for(String url : requestDto.getTweetUrls()){
            urls += (url+"<br/>");
        }

        String htmlContent = "<p> <img src = 'https://user-images.githubusercontent.com/70956926/160648926-7af1486e-82ec-4076-8961-f66a605205e9.png' height='120' width='600'>\n</p>" +
         		 "<p><br/>Hello, we are Fedi.</p>" +
                "<p>Acquaintance-type crimes including illegal synthesis are classified as digital sex crimes in accordance with Articles 14-2 and 14-3 of the Sexual Violence Punishment Act, so reporting and punishment are possible in South Korea. We are sending the original Twitter link that is believed to have uploaded the materials committed digital sex crimes required in the reporting process.<br/><br/></p>"+
                "<p><b>The original tweet links</b><br/>" + urls + "<br/></p>" +
                "<p>You can report the perpetrator with these original tweet links to <a href='https://d4u.stop.or.kr/' target = 'parent'>the Digital Sex Crime Victim Reporting Center in South Korea</a>. It helps victims by providing counseling, deletion support, and distribution status monitoring, including the reception of digital sex crime damage, investigation, legal, and medical support.<br/></p>" +
                "<p> <img src ='https://user-images.githubusercontent.com/67725652/148682922-490daafa-4a5e-4afb-87fd-ba913481a70a.png' height='463' width='600'> </p>";
        mailHandler.setText(htmlContent, true);

        mailHandler.send();
        return "success";
    }
}
