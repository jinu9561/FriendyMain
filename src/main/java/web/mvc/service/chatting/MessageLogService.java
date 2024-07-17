package web.mvc.service.chatting;


import web.mvc.dto.chat.MessageDTO;
import web.mvc.entity.chatting.MessageLog;

import java.util.List;

public interface MessageLogService {
        /*
        메세지 목록 불러오기.
         */
        List<MessageLog> messageList();

        void insertMessage(MessageDTO messageDTO);

}
