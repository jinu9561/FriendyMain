package web.mvc.service.chatting;


import web.mvc.dto.chat.ChattingRoomDTO;
import web.mvc.entity.chatting.ChattingRoom;
import web.mvc.entity.chatting.MessageLog;

import java.util.List;

public interface ChattingRoomService {

    ChattingRoom createChattingRoom(ChattingRoomDTO chattingRoomDTO) ;

//    List<Member> inviteMember(Long userId );

    String deportChat (Long userId);

    String deleteChatRoom(ChattingRoom chattingRoom);


    List<MessageLog> findMessageLog(Long chattingRoomSeq );


}
