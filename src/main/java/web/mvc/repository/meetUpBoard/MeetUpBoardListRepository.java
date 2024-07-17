package web.mvc.repository.meetUpBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.meetUpBoard.MeetUpBoardList;

import java.util.List;

public interface MeetUpBoardListRepository extends JpaRepository<MeetUpBoardList, Long> {



    @Query("select p from  MeetUpBoardList p where p.meetUpBoard.meetUpSeq=?1")
    List<MeetUpBoardList> selectMeetUpBoardListByMeetUpSeq (Long meetUpSeq);


    @Query("select  m from MeetUpBoardList  m  where m.user.userSeq=?1")
    List<MeetUpBoardList> searchMeetUpBoardListByUserSeq (Long userSeq);





}
