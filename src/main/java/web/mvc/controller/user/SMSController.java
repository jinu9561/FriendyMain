package web.mvc.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.user.SmsVerificationDTO;
import web.mvc.service.user.SmsVerificationService;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/sms")
public class SMSController {

    private final SmsVerificationService smsVerificationService;

    @PostMapping("/{userSeq}")
    public ResponseEntity<?> sendSms(@PathVariable Long userSeq , @RequestBody SmsVerificationDTO smsVerificationDTO) {
        log.info("sms 인증 발급");
        return ResponseEntity.status(HttpStatus.OK).body(smsVerificationService.sendSms(userSeq,smsVerificationDTO));
    }

    @PostMapping("/reissue/{userSeq}")
    public ResponseEntity<?> reSMSVerification(@PathVariable Long userSeq,@RequestBody SmsVerificationDTO smsVerificationDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(smsVerificationService.reSMSVerification(userSeq,smsVerificationDTO));
    }

    @PostMapping("/confirm/{userSeq}")
    public ResponseEntity<?> verifySMS(@RequestBody SmsVerificationDTO sms, @PathVariable Long userSeq){
        return ResponseEntity.status(HttpStatus.OK).body(smsVerificationService.verifySMS(sms.getSmsToken(),userSeq));
    }

}
