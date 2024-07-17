package web.mvc.repository.notification;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.mvc.entity.notification.Notification;

import java.util.List;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification, Long> {

    /**
     * 유저의 전체 알림 출력
     */
    @Query("select n from Notification n where n.user.userSeq= ?1")
    List<Notification> findAllNotificationsByUser(Long userSeq);

    /**
     * 알림 읽음처리
     */
    @Modifying
    @Transactional
    @Query("update Notification n set n.isChecked = :isChecked where n.notificationSeq = :notificationSeq")
    int updateNotificationIsChecked(@Param("isChecked") int isChecked, @Param("notificationSeq") Long notificationSeq );

    /**
     * 알림 삭제
     */
    @Modifying
    @Transactional
    @Query("delete from Notification n where n.notificationSeq= ?1")
    void deleteNotification(Long notificationSeq);

    // flush the session
    void flush();

}
