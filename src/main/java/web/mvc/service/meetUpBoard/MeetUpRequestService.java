package web.mvc.service.meetUpBoard;

import web.mvc.dto.meetUpBoard.MeetUpBoardDTO;
import web.mvc.dto.meetUpBoard.MeetUpRequestDTO;
import web.mvc.dto.user.UsersDTO;
import web.mvc.entity.meetUpBoard.MeetUpBoardList;
import web.mvc.entity.meetUpBoard.MeetUpRequest;

import java.util.List;

public interface MeetUpRequestService {

    int createMeetUpRequest(MeetUpRequestDTO meetUpRequestDTO);

    List<Long> checkValidRequest(Long meetUpRequestSeq);


    List<MeetUpRequest> findAllReqestBySeq(Long meetUpBoardSeq);

     String updateStatusByReqSeq( int meetUpRequestStatus, Long meetUpSeq , Long userSeq);

    List<UsersDTO> findMeetUpList(Long meetUpSeq);

    List<MeetUpRequest> findAllRequestByUserSeq(Long userSeq);


     void addMeetUpList (MeetUpRequestDTO meetUpRequestDTO);

     void deleteFromMeetUp( Long userSeq, Long meetUpSeq);

    void deleteRequest( Long userSeq, Long meetUpSeq);


}
