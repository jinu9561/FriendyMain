package web.mvc.service.notification;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import web.mvc.entity.notification.Notification;
import web.mvc.entity.user.Users;
import web.mvc.repository.notification.NotificationRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final NotificationRepository notificationRepository;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public List<Notification> getNotificationsByUser(Users user) {
        return notificationRepository.findAllNotificationsByUser(user.getUserSeq());
    }

    @Override
    public void updateNotificationIsChecked(Long notificationSeq) {
        notificationRepository.updateNotificationIsChecked(1, notificationSeq);
    }

    @Override
    public boolean notificationIsChecked(Long notificationSeq) {
        Notification notification = notificationRepository.findById(notificationSeq).orElse(null);
        return notification != null && notification.getIsChecked() == 1;
    }

    @Override
    public void deleteNotification(Long notificationSeq) {
        notificationRepository.deleteNotification(notificationSeq);
    }

    @Override
    public String getNotificatedUrl(Notification notification) {
        return notification.getNotificationLinkUrl();
    }

    @Override
    public Notification getNotificationById(Long notificationSeq) {
        return notificationRepository.findById(notificationSeq).orElse(null);
    }

    @Override
    public void sendNotification(Long userSeq, String message) {
        SseEmitter emitter = emitters.get(userSeq);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("report-status").data(message));
            } catch (IOException e) {
                log.error("Failed to send notification", e);
                emitters.remove(userSeq);
                emitter.completeWithError(e);
            }
        }
    }

    @Override
    public void addNotification(Users user, String message) {
        // 알림 저장
        System.out.println("@%&%^@@@@@addNotification");
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setNotificationMessage(message);
        notification.setNotificationRegDate(LocalDateTime.now());
        notification.setIsChecked(0); // 읽지 않은 상태로 설정
        notificationRepository.save(notification);

        // 실시간 알림 전송
        sendRealTimeNotification(user.getUserSeq(), message);
    }

    private void sendRealTimeNotification(Long userSeq, String message) {
        System.out.println("@%&%^@@@@@sendRealTimeNotification message" + message);
        SseEmitter emitter = emitters.get(userSeq);
        if (emitter != null) {
            executor.execute(() -> {
                try {
                    emitter.send(SseEmitter.event().name("notification").data(message));
                } catch (IOException e) {
                    log.error("알림 전송 중 오류 발생", e);
                    emitters.remove(userSeq);
                }
            });
        }
    }

    @Override
    public SseEmitter addUser(Long userSeq) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(userSeq, emitter);
        emitter.onCompletion(() -> emitters.remove(userSeq));
        emitter.onTimeout(() -> emitters.remove(userSeq));
        return emitter;
    }

    @Override
    public void removeEmitter(Long userSeq, SseEmitter emitter) {
        emitters.remove(userSeq);
        emitter.complete();
        log.info("Removed and completed emitter for userSeq: {}", userSeq);
    }
}


//@Service
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
//public class NotificationServiceImpl implements NotificationService {
//
//    private final Map<Users, SseEmitter> userEmitters = new ConcurrentHashMap<>();
//    private final ExecutorService executor = Executors.newSingleThreadExecutor();
//    private final NotificationRepository notificationRepository;
//
//    @Override
//    public List<Notification> getNotificationsByUser(Users user) {
//        return notificationRepository.findAllNotificationsByUser(user.getUserSeq());
//    }
//
//    @Override
//    public void updateNotificationIsChecked(Long notificationSeq) {
//        notificationRepository.updateNotificationIsChecked(1, notificationSeq);
//    }
//
//    @Override
//    public boolean notificationIsChecked(Long notificationSeq) {
//        Notification notification = notificationRepository.findById(notificationSeq).orElse(null);
//        return notification != null && notification.getIsChecked() == 1;
//    }
//
//    @Override
//    public void deleteNotification(Long notificationSeq) {
//        notificationRepository.deleteNotification(notificationSeq);
//    }
//
//    @Override
//    public String getNotificatedUrl(Notification notification) {
//        return notification.getNotificationLinkUrl();
//    }
//
//    @Override
//    public Notification getNotificationById(Long notificationSeq) {
//        return notificationRepository.findById(notificationSeq).orElse(null);
//    }
//
//    @Override
//    public void sendNotification(String message) {
//        // This method is no longer used in the new implementation
//    }
//
//    @Override
//    public void addNotification(Users user, String message) {
//        Notification notification = new Notification();
//        notification.setUser(user);
//        notification.setNotificationMessage(message);
//        notification.setNotificationRegDate(LocalDateTime.now());
//        notification.setIsChecked(0); // 읽지 않은 상태로 설정
//        Notification savedNotification = notificationRepository.save(notification);
//
//        sendRealTimeNotification(savedNotification);
//    }
//
//    private void sendRealTimeNotification(Notification notification) {
//        SseEmitter emitter = userEmitters.get(notification.getUser().getUserSeq());
//        if (emitter != null) {
//            executor.execute(() -> {
//                try {
//                    emitter.send(SseEmitter.event().name("notification").data(notification.getNotificationMessage()));
//                } catch (Exception e) {
//                    // 예외 처리
//                    log.error("Error sending notification", e);
//                    userEmitters.remove(notification.getUser().getUserSeq());
//                }
//            });
//        }
//    }
//
//    @Override
//    public SseEmitter addUser(Users user) {
//        SseEmitter emitter = new SseEmitter();
//        userEmitters.put(user, emitter);
//        emitter.onCompletion(() -> userEmitters.remove(user));
//        emitter.onTimeout(() -> userEmitters.remove(user));
//        return emitter;
//    }
//}
