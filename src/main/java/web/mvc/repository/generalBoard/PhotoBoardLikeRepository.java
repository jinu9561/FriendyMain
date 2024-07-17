package web.mvc.repository.generalBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.generalBoard.PhotoBoardLike;

public interface PhotoBoardLikeRepository extends JpaRepository<PhotoBoardLike,Long> {

    @Query("select phli from PhotoBoardLike phli where phli.photoBoard.photoBoardSeq =?1 and phli.user.userSeq=?2")
    public PhotoBoardLike findByUserSeq(Long photoBoardSeq,Long userSeq);
}
