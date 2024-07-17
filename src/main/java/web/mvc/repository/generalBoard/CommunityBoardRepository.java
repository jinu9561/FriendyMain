package web.mvc.repository.generalBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.generalBoard.CommunityBoard;

import java.util.List;

public interface CommunityBoardRepository extends JpaRepository<CommunityBoard, Long> {
    // boardType에 따라 커뮤니티 게시판을 조회
    List<CommunityBoard> findByBoardTypeOrderByBoardRegDateDesc(int boardType);
    //boardType에 따라 keyword를 제목이나 내용에 포함하는 커뮤니티 게시판을 조회
    List<CommunityBoard> findByBoardTypeAndBoardTitleContainingOrBoardContentContainingOrderByBoardRegDateDesc(
            int boardType, String titleKeyword, String contentKeyword);
    @Modifying
    @Query("UPDATE CommunityBoard cb SET cb.commBoardCount = cb.commBoardCount + 1 WHERE cb.commBoardSeq = :commBoardSeq")
    void updateCommBoardCount(Long commBoardSeq);
}
