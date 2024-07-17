package web.mvc.service.generalBoard;

import org.springframework.stereotype.Service;
import web.mvc.dto.generalBoard.ReplyDTO;

import web.mvc.entity.generalBoard.Reply;

import java.util.List;


public interface ReplyService {
    List<ReplyDTO> getRepliesByCommBoardSeq(Long commBoardSeq);
    List<ReplyDTO> getAllReplies();
    ReplyDTO addReply(Long commBoardSeq, ReplyDTO replyDTO);
    String deleteReply(Long replySeq);

}