package web.mvc.dto.friend;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FriendListDTO {

    private Long friendsListSeq;
    private Long userSeq;
    private Long friendUserSeq;
    private String nickName;
    private int friendStatus; // 0 : 친구 , 1 : 차단
    private LocalDateTime friendRegDate;
    private LocalDateTime friendUpdateDate;

}
