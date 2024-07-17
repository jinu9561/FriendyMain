package web.mvc.service.chatting;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import web.mvc.dto.chat.MessageDTO;
import web.mvc.entity.chatting.ChattingRoom;
import web.mvc.entity.chatting.MessageLog;
import web.mvc.entity.user.Users;
import web.mvc.repository.chatting.ChattingRoomRepository;
import web.mvc.repository.chatting.MessageLogRepository;
import web.mvc.repository.user.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RequiredArgsConstructor
@Service
public class MessageLogServiceImpl implements MessageLogService{

    private final MessageLogRepository messageLogRepository;

    @Override
    public List<MessageLog> messageList() {




        return null;
    }

    @Override
    public void insertMessage(MessageDTO messageDTO) {
        System.out.println("여기인가요");
        Users users = Users.builder().
                userSeq(messageDTO.getUserSeq()).
                build();


        ChattingRoom chattingRoom= ChattingRoom.builder().
                chattingroomSeq(messageDTO.getChattingRoomSeq()).
                build();

        System.out.println("MessageLogServiceImpl chattingRoom.getChattingroomSeq  =  "+chattingRoom.getChattingroomSeq());
        System.out.println("MessageLogServiceImpl users.getUserSeq  =  " + users.getUserSeq());
        String messageLogString =messageDTO.getChattingContent();
        JSONObject messageLogJson = new JSONObject(messageLogString);
        String message = messageLogJson.getString("message");
        System.out.println("message"+message);

        MessageLog messageLog = MessageLog.builder().
        chattingContent(message)
                .chattingroom(chattingRoom)
                .user(users)
                        .build();

        messageLogRepository.save(messageLog);
//        sendMessageToParticipants(messageDTO, chattingRoom); // 진우가 추가한 부분
    }
    //////////////////////진우가 추가한 부분//////////////////////////////////////////////////////////////
    private final UserRepository userRepository;
    private final ChattingRoomRepository chattingRoomRepository;
    private final Map<Long, SseEmitter> userEmitters = new ConcurrentHashMap<>();

    private void sendMessageToParticipants(MessageDTO messageDTO, ChattingRoom room) {
        System.out.println("@@@@@@@sendMessageToParticipants 호출!!");
        room.getParticipants().forEach(participant -> {
            if (!participant.getUser().getUserSeq().equals(messageDTO.getUserSeq())) { // Avoid sending to the sender
                SseEmitter emitter = userEmitters.get(participant.getUser().getUserSeq());
                if (emitter != null) {
                    try {
                        emitter.send(SseEmitter.event().name("new-message").data("You have unread messages."));
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                }
            }
        });
    }

    public void addEmitter(Long userId, SseEmitter emitter) {
        userEmitters.put(userId, emitter);
        emitter.onCompletion(() -> userEmitters.remove(userId));
        emitter.onTimeout(() -> userEmitters.remove(userId));
        emitter.onError((e) -> userEmitters.remove(userId));
    }

    public void removeEmitter(Long userId) {
        userEmitters.remove(userId);
    }
}
