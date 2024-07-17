package web.mvc.repository.generalBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.generalBoard.PhotoBoard;

import java.util.List;

public interface PhotoBoardRepository extends JpaRepository<PhotoBoard, Long> {

    @Query("select p from PhotoBoard p order by p.photoBoardLike desc ")
    List<PhotoBoard> findAllByOrderByPhotoBoardLikeDesc();

    @Query("select p from PhotoBoard p order by p.photoBoardRegDate desc")
    List<PhotoBoard> findAllByOrderByPhotoBoardRegDateDesc();

    @Query("select p from PhotoBoard p order by p.photoBoardUpdateDate desc ")
    List<PhotoBoard> findAllByOrderByPhotoBoardUpdateDateDesc();

    @Query("select p from PhotoBoard p where p.user.userSeq=?1 order by p.photoBoardRegDate desc ")
    List<PhotoBoard> findAllByUserSeqOrderByPhotoBoardRegDateDesc(Long userSeq);

    @Query("SELECT pb FROM PhotoBoard pb " +
            "left join fetch pb.photoBoardInterestList pbi " +
            "left join fetch pbi.interest i " +
            "WHERE i.interestCategory = ?1 " +
            "ORDER BY pb.photoBoardRegDate DESC")
    List<PhotoBoard> findAllByInterestOrderByPhotoBoardRegDateDesc(String selectInterest);
}
