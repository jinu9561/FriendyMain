package web.mvc.dto.meetUpBoard;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MeetUpRequestDTO {
    private String meetUpName;
    private Long meetUpRequestSeq;
    private Long meetUpSeq;
    private Long userSeq;
    private String meetUpPlace;
    private int meetUpRequestStatus;
    private String requestText;
    private String userNickName;
    private String meetUpRequestDate;
    private int nowEntry;
    private String refuseReason;

}
