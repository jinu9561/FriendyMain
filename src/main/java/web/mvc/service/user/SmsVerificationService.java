package web.mvc.service.user;

import web.mvc.dto.user.SmsVerificationDTO;

public interface SmsVerificationService {

    // 메시지 보내기
    public String sendSms(Long userSeq,SmsVerificationDTO smsVerificationDTO);

    // 메시지 토큰 가져오기
    public String getSMSToken(Long userSeq);

    // 메시지 재발급
    public String reSMSVerification(Long userSeq,SmsVerificationDTO smsVerificationDTO);

    // 메시지 인증확인
    public boolean verifySMS(String smsToken, Long userSeq);
}
