package web.mvc.service.notification;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.mvc.entity.notification.Notification;
import web.mvc.entity.user.Users;
import web.mvc.repository.notification.NotificationRepository;
import web.mvc.repository.user.UserRepository;

import java.util.List;

@SpringBootTest
@Slf4j
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository usersRepository;

    private Users testUser;
    private Notification testNotification;

    @BeforeEach
    @Transactional
    void setUp() {
        // 테스트 사용자 생성 및 저장
        testUser = new Users();
        testUser.setUserSeq(null); // 자동 생성되도록 null로 설정
        // 추가 사용자 설정이 필요하다면 여기서 설정
        usersRepository.save(testUser);

        // 테스트 알림 생성 및 저장
        testNotification = new Notification();
        testNotification.setUser(testUser);
        testNotification.setNotificationMessage("테스트 알림");
        testNotification.setIsChecked(0);
        testNotification.setNotificationLinkUrl("http://www.naver.com");
        notificationRepository.save(testNotification);
        notificationRepository.flush(); // flush to synchronize the session
    }

    @Test
    @Transactional
    void testGetNotificationsByUser() {
        List<Notification> notifications = notificationService.getNotificationsByUser(testUser);
        log.info("Notifications: {}", notifications);
    }

    @Test
    @Transactional
    void testUpdateNotificationIsChecked() {
        notificationService.updateNotificationIsChecked(testNotification.getNotificationSeq());
        notificationRepository.flush(); // flush to synchronize the session
        Notification updatedNotification = notificationRepository.findById(testNotification.getNotificationSeq()).orElse(null);
        log.info("Updated Notification: {}", updatedNotification);
    }

    @Test
    @Transactional
    void testNotificationIsChecked() {
        boolean isChecked = notificationService.notificationIsChecked(testNotification.getNotificationSeq());
        log.info("Is Checked Before Update: {}", isChecked);
        notificationService.updateNotificationIsChecked(testNotification.getNotificationSeq());
        notificationRepository.flush(); // flush to synchronize the session
        isChecked = notificationService.notificationIsChecked(testNotification.getNotificationSeq());
        log.info("Is Checked After Update: {}", isChecked);
    }

    @Test
    @Transactional
    void testDeleteNotification() {
        notificationService.deleteNotification(testNotification.getNotificationSeq());
        notificationRepository.flush(); // flush to synchronize the session
        Notification deletedNotification = notificationRepository.findById(testNotification.getNotificationSeq()).orElse(null);
        log.info("Deleted Notification: {}", deletedNotification);
    }

    @Test
    @Transactional
    void testGetNotificatedUrl() {
        String url = notificationService.getNotificatedUrl(testNotification);
        log.info("Notification URL: {}", url);
    }

    @Test
    @Transactional
    void testGetNotificationById() {
        Notification notification = notificationService.getNotificationById(testNotification.getNotificationSeq());
        log.info("Notification by ID: {}", notification);
    }
}