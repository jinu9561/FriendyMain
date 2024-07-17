package web.mvc.controller.generalBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.generalBoard.ReplyDTO;
import web.mvc.service.generalBoard.ReplyService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/community-boards/{commBoardSeq}/replies")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    //댓글 생성
    @PostMapping
    public ResponseEntity<ReplyDTO> addReply(@PathVariable Long commBoardSeq, @RequestBody ReplyDTO replyDTO){
        return ResponseEntity.ok(replyService.addReply(commBoardSeq, replyDTO));
    }

    // 게시물에 대한 모든 댓글 조회
    @GetMapping
    public ResponseEntity<List<ReplyDTO>> getRepliesByBoardSeq(@PathVariable Long commBoardSeq){
        return ResponseEntity.ok(replyService.getRepliesByCommBoardSeq(commBoardSeq));
    }

    //특정 id(replySeq)를 가진 댓글 삭제
    @DeleteMapping("/{replySeq}")
    public String deleteReply(@PathVariable Long replySeq){
        return replyService.deleteReply(replySeq);
    }

    // 전체 댓글 조회
    @GetMapping("/all")
    public ResponseEntity<List<ReplyDTO>> getAllReplies(){
        return ResponseEntity.ok(replyService.getAllReplies());
    }




}
