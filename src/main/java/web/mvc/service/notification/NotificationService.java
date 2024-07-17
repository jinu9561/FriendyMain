package web.mvc.service.notification;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import web.mvc.entity.notification.Notification;
import web.mvc.entity.user.Users;

import java.util.List;

public interface NotificationService {

    /**
     * 유저의 전체 알림 출력
     * */
    List<Notification> getNotificationsByUser(Users user);

    /**
     * 알림 읽음처리
     */
    void updateNotificationIsChecked(Long notificationSeq);

    /**
     * 읽지않은 알림이 있으면 boolean 값 true, 없으면 false
     * */
    boolean notificationIsChecked(Long notificationSeq);

    /**
     * 알림 삭제
     * */
    void deleteNotification(Long notificationSeq);

    /**
     * 알림에 뜬 링크로 이동하는 url 반환
     */
    String getNotificatedUrl(Notification notification);

    Notification getNotificationById(Long notificationSeq);

    void sendNotification(Long userSeq, String message);

    /**
     * 알림 추가 메소드
     * */
    void addNotification(Users user, String message);

    SseEmitter addUser(Long userSeq);

    void removeEmitter(Long userSeq, SseEmitter emitter);
}
