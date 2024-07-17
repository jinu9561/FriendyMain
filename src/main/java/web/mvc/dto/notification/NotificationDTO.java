package web.mvc.dto.notification;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private Long notificationSeq;
    private Long userSeq;
    private String notificationMessage;
    private int isChecked; // 0=미확인, 1=확인
    private String notificationLinkUrl;
    private LocalDateTime notificationRegDate;

}
