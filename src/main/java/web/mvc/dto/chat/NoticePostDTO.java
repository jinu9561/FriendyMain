package web.mvc.dto.chat;


import lombok.*;

import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NoticePostDTO {
    private Long noticePostSeq;

    private String noticeContent;

    private Date noticeCreateDate;

    private String roomId;

    private Long chattingRoomSeq;

}


