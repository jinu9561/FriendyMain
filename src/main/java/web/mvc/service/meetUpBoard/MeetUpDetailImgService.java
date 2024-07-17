package web.mvc.service.meetUpBoard;

import web.mvc.entity.meetUpBoard.MeetUpBoardDetailImg;

import java.util.List;

public interface MeetUpDetailImgService {

    List<MeetUpBoardDetailImg> findImgList(Long meetUpSeq);
}
