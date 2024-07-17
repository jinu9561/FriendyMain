package web.mvc.controller.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.service.chatbot.AuthService;
import web.mvc.service.chatbot.CustomMessageService;
import web.mvc.service.chatbot.KakaoAuthService;

@RestController
public class BaseController {

    @Autowired
    AuthService authService;

    @Autowired
    KakaoAuthService kakaoAuthService;

    @Autowired
    CustomMessageService customMessageService;

    @GetMapping("/")
    public String serviceStart(String code) {
        if (authService.getKakaoAuthToken(code)) {

            String accessToken = AuthService.authToken;
            System.out.println("accessToken = " + accessToken);

            if (kakaoAuthService.verifyAccessToken(accessToken)) {
                customMessageService.sendMyMessage();
                return "메시지 전송 성공";
            } else {
                return "유효하지 않은 토큰";
            }
        } else {
            return "토큰 발급 실패";
        }
    }
}
