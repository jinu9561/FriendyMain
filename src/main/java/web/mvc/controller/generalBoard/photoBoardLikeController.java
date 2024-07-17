package web.mvc.controller.generalBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.service.generalBoard.PhotoBoardLikeService;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
@Slf4j
public class photoBoardLikeController {

    private final PhotoBoardLikeService photoBoardLikeService;

    // 좋아요 등록
    @PostMapping("/{photoBoardSeq}/{userSeq}")
    public ResponseEntity<?> insertLike(@PathVariable Long photoBoardSeq,@PathVariable Long userSeq){
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardLikeService.insertLike(photoBoardSeq,userSeq));

    }

    // 좋아요 삭제
    @DeleteMapping("/{photoBoardSeq}/{userSeq}")
    public ResponseEntity<?> deleteLike(@PathVariable Long photoBoardSeq,@PathVariable Long userSeq){
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardLikeService.deleteLike(photoBoardSeq,userSeq));
    }

    // 좋아요 등록 상태 검사
    @GetMapping("/{photoBoardSeq}/{userSeq}")
    public ResponseEntity<?> isCheck(@PathVariable Long photoBoardSeq, @PathVariable Long userSeq){
        log.info("좋아요 검사");
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardLikeService.isCheck(photoBoardSeq,userSeq));
    }

}
