package web.mvc.repository.generalBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.generalBoard.PhotoBoardInterest;
import web.mvc.entity.user.ProfileInterest;

import java.util.List;

public interface PhotoBoardInterestRepository  extends JpaRepository<PhotoBoardInterest,Long> {

    @Query("select phi from PhotoBoardInterest phi where phi.photoBoard.photoBoardSeq = ?1")
    public List<PhotoBoardInterest> findByPhotoBoardSeq(Long photoBoardSeq);

    @Modifying
    @Query("delete from PhotoBoardInterest phi where phi.photoBoard.photoBoardSeq = ?1")
    public int deleteByPhotoBoardSeq(Long photoBoardSeq);
}
