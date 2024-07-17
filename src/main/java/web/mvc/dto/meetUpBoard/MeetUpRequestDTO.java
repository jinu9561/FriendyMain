package web.mvc.dto.meetUpBoard;
import lombok.*;

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
    private int meetUpRequestStatus;
    private String requestText;
    private String userNickName;
    private int nowEntry;

}
