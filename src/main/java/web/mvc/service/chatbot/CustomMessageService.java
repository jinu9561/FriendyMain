package web.mvc.service.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.dto.chatbot.DefaultMessageDto;

@Service
public class CustomMessageService {

    @Autowired
    MessageService messageService;

    public boolean sendMyMessage() {
        DefaultMessageDto myMsg = new DefaultMessageDto();
        myMsg.setBtnTitle("자세히보기");
        myMsg.setObjType("text");
        myMsg.setText("메시지 테스트입니다.");

        DefaultMessageDto.Link link = new DefaultMessageDto.Link();
//        link.setWebUrl("http://pf.kakao.com/_SxkLcG");
//        link.setMobileUrl("http://pf.kakao.com/_SxkLcG");

        link.setWebUrl("http://localhost:9000");
        link.setMobileUrl("http://localhost:9000");

        myMsg.setLink(link);

        String accessToken = AuthService.authToken;
        System.out.println("CustomMessageService myMsg = " + myMsg);
        System.out.println("CustomMessageService   accessToken  = " + accessToken);
        return messageService.sendMessage(accessToken, myMsg);
    }
}

