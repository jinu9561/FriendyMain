package web.mvc.dto.friend;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDTO {

    private Long friendRequestSeq;
    private Long senderSeq;
    private Long receiverSeq;
    private int requestStatus;
    private LocalDateTime requestRegDate;
    private LocalDateTime requestUpdateDate;
    private String senderName;

}
