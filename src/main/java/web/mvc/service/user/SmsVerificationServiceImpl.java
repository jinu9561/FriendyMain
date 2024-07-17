package web.mvc.service.user;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import web.mvc.dto.user.SmsVerificationDTO;
import web.mvc.entity.user.SmsVerification;
import web.mvc.entity.user.Users;
import web.mvc.enums.users.State;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.user.SmsverificationRepository;
import web.mvc.repository.user.UserRepository;

import java.util.concurrent.atomic.AtomicBoolean;


@Service
@Transactional
@Slf4j
public class SmsVerificationServiceImpl implements SmsVerificationService {

    @Value("${coolsms.apiKey}")
    private String apiKey;
    @Value("${coolsms.apiSecret}")
    private String apiSecret;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SmsverificationRepository smsverificationRepository;


    private DefaultMessageService messageService;

    private String smsMeg = "본인 인증을 위해 회원 번호로 인증번호를 전송하였습니다.";
    private String reSuccessMsg = "SMS 인증번호 재발급이 완료 되었습니다";
    private String fromPhone ="01085505110";

    @PostConstruct
    public void init() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    @Override
    public String sendSms(Long userSeq, SmsVerificationDTO smsVerificationDTO) {
        Users users = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));

        SmsVerification smsVerification = new SmsVerification(users);
        SmsVerification savedSms = smsverificationRepository.save(smsVerification);
        String content ="다음 인증 코드를 사용하여 본인인증을 완료하세요: {code}";
        content = content.replace("{code}", savedSms.getSmsToken());

        Message message = new Message();
        String phoneNum = smsVerificationDTO.getPhoneNumber().replaceAll("-","");
        // 전송할 메시지 설정
        message.setFrom(fromPhone);
        message.setTo(phoneNum);
        message.setText(content);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        return smsMeg;
    }

    @Override
    public String getSMSToken(Long userSeq) {

        SmsVerification smsVerification = smsverificationRepository.findByUserSeq(userSeq).orElseThrow(
                ()->new GlobalException(ErrorCode.NOTFOUND_SMSTOKEN)
        );
        return smsVerification.getSmsToken();
    }

    @Override
    public String reSMSVerification(Long userSeq,SmsVerificationDTO smsVerificationDTO) {
        Users user = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));
        String smsCode = this.getSMSToken(userSeq);

        String content ="다음 인증 코드를 사용하여 본인인증을 완료하세요: {code}";
        content = content.replace("{code}", smsCode);

        Message message = new Message();
        String phoneNum = smsVerificationDTO.getPhoneNumber().replaceAll("-","");
        // 전송할 메시지 설정
        message.setFrom(fromPhone);
        message.setTo(phoneNum);
        message.setText(content);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        return reSuccessMsg;
    }

    @Override
    public boolean verifySMS(String smsToken,Long userSeq) {
        log.info("smsToken : "+smsToken);
        AtomicBoolean result = new AtomicBoolean(false);
        smsverificationRepository.findBySmsToken(smsToken, userSeq).ifPresent(
                smsverification -> {
                    Users users = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));
                    users.getUserDetail().setUserState(State.CERTIFIED);
                    result.set(true);
                }
        );
        return result.get();
    }


}
