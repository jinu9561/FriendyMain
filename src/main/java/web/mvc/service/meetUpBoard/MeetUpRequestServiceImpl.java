package web.mvc.service.meetUpBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.dto.meetUpBoard.MeetUpRequestDTO;
import web.mvc.dto.user.UsersDTO;
import web.mvc.entity.meetUpBoard.MeetUpBoard;
import web.mvc.entity.meetUpBoard.MeetUpBoardList;
import web.mvc.entity.meetUpBoard.MeetUpRequest;
import web.mvc.entity.user.Users;
import web.mvc.repository.meetUpBoard.MeetUpBoardListRepository;
import web.mvc.repository.meetUpBoard.MeetUpBoardRepository;
import web.mvc.repository.meetUpBoard.MeetUpRequestRepository;
import web.mvc.repository.user.UserRepository;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MeetUpRequestServiceImpl implements MeetUpRequestService {

    final MeetUpBoardService meetUpBoardService;
    final MeetUpRequestRepository meetUpRequestRepository;
    final MeetUpBoardRepository meetUpBoardRepository;
    final UserRepository userRepository;
    private final MeetUpBoardListRepository meetUpBoardListRepository;


    @Override
    public int createMeetUpRequest(MeetUpRequestDTO meetUpRequestDTO) {

        Long seq = meetUpRequestDTO.getMeetUpSeq();
        System.out.println("seq서비스" + seq);
        Long userSeq = meetUpRequestDTO.getUserSeq();
        //시퀀스로 최대인원 값 가져오고 , 최대인원의 값하고 현재 참여된 인원의 수가 같으면
        // 에러 메세지 발생 -> 인원이 가득찼습니다.
        MeetUpBoard meetUpBoardInfo = meetUpBoardService.findMeetUpByBoardSeq(seq);
        System.out.println("밋업" + meetUpBoardInfo);
        int maxEntry = meetUpBoardInfo.getMeetUpMaxEntry();
        System.out.println("맥스앤트리 서비스" + maxEntry);

        List<MeetUpBoardList> meetUpBoardLists = meetUpBoardListRepository.selectMeetUpBoardListByMeetUpSeq(seq);
        for (MeetUpBoardList meetUpBoardList : meetUpBoardLists) {
            meetUpBoardList.getUser().getUserSeq();
            if (meetUpBoardList.getUser().getUserSeq() == userSeq) {
                return 2;
            } else if (meetUpBoardList.getUser().getUserSeq() != userSeq) {

                Users users = Users.builder()
                        .userSeq(meetUpRequestDTO.getUserSeq())
                        .build();
                MeetUpRequest meetUpRequest = MeetUpRequest.builder()
                        .meetUpBoard(meetUpBoardInfo)
                        .user(users)
                        .requestText(meetUpRequestDTO.getRequestText()).
                        build();
                meetUpRequestRepository.save(meetUpRequest);
                return 1;
            }

        }

        return 0;
    }

    @Override
    public List<Long> checkValidRequest(Long meetUpSeq) {
        System.out.println(meetUpSeq + "서비스단 시퀀스");
        List<Long> list = meetUpRequestRepository.findUserSeqByMeetUpReqSeq(meetUpSeq);
        for (Long seq : list) {
            // 각 seq 값을 사용하는 로직 작성
            System.out.println(seq);
        }
        return list;

    }


    public List<MeetUpRequest> findAllReqestBySeq(Long meetUpBoardSeq) {

        List<MeetUpRequest> list = meetUpRequestRepository.findAllByMeetUpSeq(meetUpBoardSeq);

        return list;

    }

    //소모임 신청서 상태 변경
    public String updateStatusByReqSeq(int meetUpRequestStatus, Long meetUpSeq, Long userSeq) {
        System.out.println("meetUpReqeustStatus" + meetUpRequestStatus);
        int result = meetUpRequestRepository.changeStatusBySeq(meetUpRequestStatus, meetUpSeq, userSeq);
        return null;
    }

    @Override
    public List<UsersDTO> findMeetUpList(Long meetUpSeq) {
        List<MeetUpBoardList> meetUpBoardLists = meetUpBoardListRepository.selectMeetUpBoardListByMeetUpSeq(meetUpSeq);
        List<UsersDTO> usersDTOS = new ArrayList<>();
        for (MeetUpBoardList meetUpBoardList : meetUpBoardLists) {
            System.out.println("Test__" + meetUpBoardList.getMeetUpBoard().getMeetUpName());
            UsersDTO usersDTO = UsersDTO.builder()
                    .nickName(meetUpBoardList.getUser().getNickName())
                    .email(meetUpBoardList.getUser().getEmail())
                    .country(meetUpBoardList.getUser().getCountry())
                    .gender(meetUpBoardList.getUser().getGender())
                    .phone(meetUpBoardList.getUser().getPhone())
                    .build();
            usersDTOS.add(usersDTO);
            System.out.println(usersDTO + "userDTO");
        }
        return usersDTOS;
    }

    @Override
    public List<MeetUpRequest> findAllRequestByUserSeq(Long userSeq) {

        List<MeetUpRequest> meetUpRequestList=meetUpRequestRepository.findMeetUpRequestByUserSeq(userSeq);

        return meetUpRequestList;
    }


    @Override
    public void addMeetUpList(MeetUpRequestDTO meetUpRequestDTO) {
        MeetUpBoard meetUpBoard = MeetUpBoard.
                builder()
                .meetUpSeq(meetUpRequestDTO.getMeetUpSeq())
                .build();
        Users users = Users.builder()
                .userSeq(meetUpRequestDTO.getUserSeq()).build();
        MeetUpBoardList meetUpBoardList = MeetUpBoardList.builder()
                .meetUpBoard(meetUpBoard)
                .user(users)
                .build();

        meetUpBoardListRepository.save(meetUpBoardList);

    }




    @Override
    public void deleteFromMeetUp(Long userSeq, Long meetUpSeq) {
        // 해당 소모임에서 삭제하기위한 소모임의 seq , 유저의 seq,
        System.out.println("userSeq:"+userSeq + "meetUpSeq:"+meetUpSeq);
        meetUpRequestRepository.deleteAllByMeetUpBoardListSeq(meetUpSeq, userSeq);

    }

    @Override
    public void deleteRequest(Long userSeq, Long meetUpSeq) {
        System.out.println("삭제 2트째");
        System.out.println("userSeq:"+userSeq + "meetUpSeq:"+meetUpSeq);

        meetUpRequestRepository.deleteMeetUpRequestBySeq(userSeq,meetUpSeq);
    }


}
