package web.mvc.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class SseEmitterService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter addUser(Long userSeq) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(userSeq, emitter);
        emitter.onCompletion(() -> emitters.remove(userSeq));
        emitter.onTimeout(() -> emitters.remove(userSeq));
        return emitter;
    }

    public void sendNotification(Long userSeq, String message) {
        SseEmitter emitter = emitters.get(userSeq);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("notification").data(message));
            } catch (IOException e) {
                log.error("알림 전송 중 오류 발생", e);
                emitters.remove(userSeq);
            }
        }
    }
}
