package web.mvc.service.meetUpBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.entity.meetUpBoard.MeetUpBoardDetailImg;
import web.mvc.repository.meetUpBoard.MeetUpBoardDetailImgRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MeetUpDetailImgServiceImpl implements  MeetUpDetailImgService{

    private final MeetUpBoardDetailImgRepository meetUpBoardDetailImgRepository;

    @Override
    public List<MeetUpBoardDetailImg> findImgList(Long meetUpSeq) {

     List<MeetUpBoardDetailImg>list= meetUpBoardDetailImgRepository.findAllByMeetUpBoardSeq(meetUpSeq);

        return list;
    }
}
