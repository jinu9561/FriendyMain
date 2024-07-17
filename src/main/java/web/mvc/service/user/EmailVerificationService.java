package web.mvc.service.user;

import jakarta.mail.MessagingException;
import web.mvc.dto.user.EmailVerificationDTO;
import web.mvc.entity.user.EmailVerification;
import web.mvc.entity.user.Users;

public interface EmailVerificationService {

    // 이메일 확인
    public boolean verifyEmail(EmailVerificationDTO emailVerificationDTO);

    // 이메일 토큰 저장
    public EmailVerification saveEmailToken(Users user);

    // 이메일 보내기
    public void sendEmailVerificationCode(String to, String subject, String text) throws MessagingException;

    // 이메일 토큰 찾기
    public String getEmailToken(Long userSeq);

    // 이메일 재발급
    public String reEmailVerification(EmailVerificationDTO emailVerificationDTO);
}
