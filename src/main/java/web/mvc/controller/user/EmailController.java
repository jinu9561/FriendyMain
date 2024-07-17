package web.mvc.controller.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.user.EmailVerificationDTO;
import web.mvc.service.user.EmailVerificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@Slf4j
public class EmailController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("")
    public ResponseEntity<?> verifyEmail(@RequestBody EmailVerificationDTO emailVerificationDTO) {
        log.info("token: " + emailVerificationDTO.getEmailToken());
        return ResponseEntity.status(HttpStatus.CREATED).body(emailVerificationService.verifyEmail(emailVerificationDTO));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reEmailVerification(@RequestBody EmailVerificationDTO emailVerificationDTO) {
        log.info("이메일 재발급");
        return ResponseEntity.status(HttpStatus.OK).body(emailVerificationService.reEmailVerification(emailVerificationDTO));
    }
}
