package web.mvc.entity.notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import web.mvc.entity.user.Users;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    @SequenceGenerator(name = "notification_seq", sequenceName = "notification_seq", allocationSize = 1)
    private Long notificationSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")  // 외래키 지정
    private Users user;

    @Column(length = 500)
    private String notificationMessage;

    private int isChecked; // 0=미확인, 1=확인

    @Column(length = 200)
    private String notificationLinkUrl;

    @CreationTimestamp
    private LocalDateTime notificationRegDate;

}
