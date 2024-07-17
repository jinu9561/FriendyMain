package web.mvc.repository.meetUpBoard;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.meetUpBoard.MeetUpBoardDetailImg;

import java.util.List;

public interface MeetUpBoardDetailImgRepository extends JpaRepository<MeetUpBoardDetailImg, Long> {


    @Query("select m from MeetUpBoardDetailImg m where m.meetUpBoard.meetUpSeq = ?1")
    List<MeetUpBoardDetailImg> findAllByMeetUpBoardSeq(Long meetUpSeq);

    @Transactional
    @Modifying
    @Query("DELETE FROM MeetUpBoardDetailImg m WHERE m.meetUpBoard.meetUpSeq = ?1")
    void deleteAllByMeetUpBoardSeq(Long meetUpSeq);
}
