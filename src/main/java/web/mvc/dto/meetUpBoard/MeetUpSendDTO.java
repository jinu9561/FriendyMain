package web.mvc.dto.meetUpBoard;

import lombok.*;
import web.mvc.entity.meetUpBoard.MeetUpBoardDetailImg;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MeetUpSendDTO {
    private Long meetUpSeq;
    private Long userSeq;
    private String interestCate;
    private Long interestSeq;
    private String meetUpName;
    private String meetUpDesc;
    private List<Long> meetUpPeopleList;
    private List<String> meetUpBoardDetailImgNameList;
    private int meetUpPwd;
    private int meetUpMaxEntry;
    private int nowEntry;
    private String userName;
    private String meetUpRegDate;
    private  String  meetUpDeadLine;
    private int  meetUpStatus;
    private Long chattingRoomSeq;
    private String roomId;
}
