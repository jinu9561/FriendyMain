package web.mvc.repository.generalBoard;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.generalBoard.CommunityBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityBoardRepository extends JpaRepository<CommunityBoard, Long> {

    List<CommunityBoard> findByBoardTypeOrderByBoardRegDateDesc(int boardType);

    List<CommunityBoard> findByBoardTypeAndBoardTitleContainingOrBoardContentContainingOrderByBoardRegDateDesc(
            int boardType, String titleKeyword, String contentKeyword);

    @Modifying
    @Query("UPDATE CommunityBoard cb SET cb.commBoardCount = cb.commBoardCount + 1 WHERE cb.commBoardSeq = :commBoardSeq")
    void updateCommBoardCount(Long commBoardSeq);

    @Modifying
    @Transactional
    @Query("UPDATE CommunityBoard cb SET cb.boardLike = cb.boardLike + 1 WHERE cb.commBoardSeq = :commBoardSeq")
    void incrementLikes(Long commBoardSeq);

    @Modifying
    @Transactional
    @Query("UPDATE CommunityBoard cb SET cb.boardLike = cb.boardLike - 1 WHERE cb.commBoardSeq = :commBoardSeq")
    void decrementLikes(Long commBoardSeq);
}

