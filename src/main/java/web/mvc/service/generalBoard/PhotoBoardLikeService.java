package web.mvc.service.generalBoard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface PhotoBoardLikeService {


    // 좋아요 등록
    public int insertLike(Long photoBoardSeq,Long userSeq);

    // 좋아요 삭제
    public int deleteLike(Long photoBoardSeq,Long userSeq);

    // 좋아요 등록 상태 검사
    public boolean isCheck(Long photoBoardSeq, Long userSeq);


}
