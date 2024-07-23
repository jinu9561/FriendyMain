package web.mvc.repository.meetUpBoard;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.meetUpBoard.MeetUpRequest;

import java.util.List;

public interface MeetUpRequestRepository extends JpaRepository<MeetUpRequest, Long> {

    @Query("select p from MeetUpRequest p where p.meetUpBoard.meetUpSeq = ?1 order by p.meetUpReqeustRegDate asc")
    List<MeetUpRequest> findAllByMeetUpSeq(Long meetUpBoardSeq);

    @Modifying
    @Query("update MeetUpRequest p set p.meetUpRequestStatus =  ?1 , p.reasonText = ?4 where p.meetUpBoard.meetUpSeq= ?2 and  p.user.userSeq=?3")
    int changeStatusBySeq( int meetUpRequestStatus, Long meetUpSeq, Long userSeq,  String refuseReason);

    @Query("select  p.user.userSeq from MeetUpRequest p where  p.meetUpBoard.meetUpSeq=?1")
    List<Long> findUserSeqByMeetUpReqSeq(Long meetUpReqSeq);


    @Query("select m from MeetUpRequest  m where  m.user.userSeq=?1 order by m.meetUpReqeustRegDate asc ")
    List<MeetUpRequest> findMeetUpRequestByUserSeq(Long userSeq);

    @Modifying
    @Transactional
    @Query("delete from MeetUpBoardList where meetUpBoard.meetUpSeq=?2 and user.userSeq=?1")
    void deleteAllByMeetUpBoardListSeq(Long userSeq, Long meetUpBoardSeq);


    @Modifying
    @Transactional
    @Query("delete from MeetUpRequest where  user.userSeq=?1 and meetUpBoard.meetUpSeq=?2")
    void deleteMeetUpRequestBySeq(Long userSeq , Long meetUpSeq);
    // userSeq : 2 meetUpSeq : 5







}
