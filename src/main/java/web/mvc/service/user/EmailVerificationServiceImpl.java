package web.mvc.service.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import web.mvc.dto.user.EmailVerificationDTO;
import web.mvc.entity.user.EmailVerification;
import web.mvc.entity.user.Users;
import web.mvc.enums.users.State;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.user.EmaillVerificationRepository;
import web.mvc.repository.user.UserRepository;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmaillVerificationRepository emailVerificationRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    private String reSubject ="Friendy 가입 인증코드 재발급 입니다!";
    private String reContent ="다음 인증 코드를 사용하여 회원가입을 완료하세요: {code}";
    private String reSuccessMsg = "Email 인증번호 재발급이 완료 되었습니다";
    private String failMsg = "메일 발송을 실패했습니다";
    private String reFailMsg = "회원 가입후 이용해 주세요";


    @Override
    public boolean verifyEmail(EmailVerificationDTO emailVerificationDTO) {
        log.info("Verify email : {}", emailVerificationDTO.getEmailToken());
        AtomicBoolean result = new AtomicBoolean(false);

        emailVerificationRepository.findByEmailToken(emailVerificationDTO.getEmailToken(),emailVerificationDTO.getUserId()).ifPresent(
                emailVerification -> {
                    Users users = userRepository.findUserByUserId(emailVerificationDTO.getUserId());
                    if(users != null){
                        users.setRole("ROLE_USER");
                        users.getUserDetail().setUserState(State.NOMAL);
                        result.set(true);
                    }
                }
        );

        return result.get();
    }

    @Override
    public EmailVerification saveEmailToken(Users user) {
        EmailVerification emailVerification = new EmailVerification(user);
        return emailVerificationRepository.save(emailVerification);
    }

    @Override
    public void sendEmailVerificationCode(String to, String subject, String text) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        mailSender.send(message);
    }

    @Override
    public String getEmailToken(Long userSeq) {

        EmailVerification emailVerification = emailVerificationRepository.findByUserSeq(userSeq).orElseThrow(
                () -> new GlobalException(ErrorCode.NOTFOUND_EMAILTOKEN)
        );

        return emailVerification.getEmailToken();
    }

    @Override
    public String reEmailVerification(EmailVerificationDTO emailVerificationDTO) {

        Users user = userRepository.findUserByUserId(emailVerificationDTO.getUserId());
        log.info("아이디 : " + emailVerificationDTO.getUserId());
        log.info("user : " + user);
        if(user == null){
            log.info("해당 유저가 없다!");
            return reFailMsg;
        }


        String reCode = this.getEmailToken(user.getUserSeq());
        reContent = reContent.replace("{code}",reCode);

        try{
            this.sendEmailVerificationCode(emailVerificationDTO.getEmail(),reSubject,reContent);
        }catch (MessagingException e){
            return failMsg;
        }
        return reSuccessMsg;



    }


}
