package web.mvc.dto.qna;

import lombok.*;
import web.mvc.dto.user.UsersDTO;
import web.mvc.entity.qna.Qna;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QnaDTO {

    private Long qnaSeq;
    private String qnaDesc;
    private int qnaStatus;
    private LocalDateTime qnaUpdateDate;
    private LocalDateTime qnaRegDate;
    private String qnaReply;

    private UsersDTO user;

    public QnaDTO(Qna qna) {
        qnaSeq = qna.getQnaSeq();
        qnaDesc = qna.getQnaDesc();
        qnaStatus = qna.getQnaStatus();
        qnaUpdateDate = qna.getQnaUpdateDate();
        qnaRegDate = qna.getQnaRegDate();
        qnaReply = qna.getQnaReply();

    }

}
