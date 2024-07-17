package web.mvc.repository.generalBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.generalBoard.PhotoBoardDetailImg;

import java.util.List;

public interface PhotoBoardDetailImgRepository extends JpaRepository<PhotoBoardDetailImg, Long> {

    @Query("select pd from PhotoBoardDetailImg pd where pd.photoBoard.photoBoardSeq=?1")
    List<PhotoBoardDetailImg> findPhotoDetailImgByPhotoBoardSeq (Long photoBoardSeq);


}
