package web.mvc.dto.chat;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChattingRoomDTO {
    Long chattingroomSeq;
    String userId;
    String roomId;
    String chattingRoomId;

    Date roomRegDate;
}
