package web.mvc.dto.generalBoard;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import org.springframework.stereotype.Component;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.generalBoard.Reply;

import web.mvc.entity.user.Users;
import web.mvc.repository.generalBoard.CommunityBoardRepository;
import web.mvc.repository.user.UserRepository;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDTO {

    private Long replySeq;
    private Long userSeq;
    private Long commBoardSeq;
    private String replyContent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") //Json으로 보낼 때, LocalDateTime을 String으로 변환
    private LocalDateTime replyRegDate;
    private String nickName;



    public Reply toEntity(Users user, CommunityBoard communityBoard){
        return Reply.builder()
                .replySeq(this.replySeq)
                .user(user)
                .communityBoard(communityBoard)
                .replyContent(this.replyContent)
                .build();
    }
}
