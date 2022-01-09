package com.fedi.web;

import com.fedi.service.MailService;
import com.fedi.web.dto.MailRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MailController {
    private final MailService mailService;

    @PostMapping("/mail")
    public ResponseEntity<Object> sendMail(@RequestBody MailRequestDto requestDto){
        try{
            return ResponseEntity.ok().body(mailService.sendMail(requestDto));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
        }
    }
}
