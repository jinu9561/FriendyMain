package web.mvc.service.chatting;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.mvc.dto.chat.ChattingRoomDTO;
import web.mvc.entity.chatting.ChattingRoom;
import web.mvc.entity.chatting.MessageLog;
import web.mvc.entity.user.Users;
import web.mvc.repository.chatting.ChattingRoomRepository;
import web.mvc.repository.chatting.MessageLogRepository;
import web.mvc.repository.user.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class ChattingRoomSerivceImpl implements ChattingRoomService {

   private final ChattingRoomRepository chattingRoomRepository;
   private final MessageLogRepository messageLogRepository;
    private final UserRepository userRepository;


    @Override
    public ChattingRoom createChattingRoom(ChattingRoomDTO chattingRoomDTO) {
        Users users = userRepository.findUserByUserId(chattingRoomDTO.getUserId());
        String userNickname = users.getNickName();
        LocalDate today = LocalDate.now();
        int randomNumber = new Random().nextInt(100000);
        String roomId = userNickname + today.getMonthValue() + today.getDayOfMonth() + randomNumber;
        ChattingRoom chattingRoom = ChattingRoom.builder()
                .roomId(roomId)
                .build();
         chattingRoomRepository.save(chattingRoom);

        return chattingRoom;
    }

    @Override
    public String deportChat(Long userId) {
        return null;
    }

    @Override
    public String deleteChatRoom(ChattingRoom chattingRoom) {
        return null;
    }
    //채팅룸에서 나가기 버튼 누르면 해당 방의 채팅 내역 삭제. 유저 시퀀스의

    @Override
    public List<MessageLog> findMessageLog(Long chattingRoomSeq) {
        List<MessageLog> list= messageLogRepository.findMessageLogByRoomId(chattingRoomSeq);
        return list;
    }
    /////////////////////////////////////진우가 만든거///////////////////////////////////////////
    private String generateRoomId(String userNickname) {
        System.out.println("!!#!@#@#!@!@generateRoomId 호출됨!!!");
        LocalDate today = LocalDate.now();
        int randomNumber = new Random().nextInt(100000);
        return userNickname + today.getMonthValue() + today.getDayOfMonth() + randomNumber;
    }

}
