package web.mvc.dto.meetUpBoard;

import lombok.*;
import web.mvc.entity.meetUpBoard.MeetUpBoardDetailImg;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MeetUpUpdateDTO {
    private Long meetUpSeq;
    private String meetUpName;
    private String meetUpDesc;
    private Long interestSeq;
    private int meetUpPwd;
    private Long meetUpMainImg;
    private int meetUpMaxEntry;
    private String meetUpDeadLine;
    private int nowEntry;

}