package web.mvc.service.meetUpBoard;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.meetUpBoard.MeetUpBoardDTO;
import web.mvc.dto.meetUpBoard.MeetUpRequestDTO;
import web.mvc.dto.meetUpBoard.MeetUpSendDTO;
import web.mvc.dto.meetUpBoard.MeetUpUpdateDTO;
import web.mvc.entity.chatting.ChattingRoom;
import web.mvc.entity.meetUpBoard.MeetUpBoard;
import web.mvc.entity.meetUpBoard.MeetUpBoardDetailImg;
import web.mvc.entity.meetUpBoard.MeetUpRequest;
import web.mvc.entity.user.Interest;
import web.mvc.entity.user.Users;
import web.mvc.repository.meetUpBoard.MeetUpBoardRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class meetUpBoardTest {
    @Autowired
    private MeetUpBoardServiceImpl meetUpBoardService;

    @Autowired
    private MeetUpBoardRepository meetUpBoardRepository;

    private Long userSeq = 1L;
    private Long interestSeq = 1L;
    private String date = "2024-07-20T18:24";
    private MeetUpBoardDetailImg meetUpBoardDetailImg = new MeetUpBoardDetailImg();
    private List<MultipartFile> files = new ArrayList<>();
    private List<MeetUpBoardDetailImg> meetUpBoardDetailImgs = new ArrayList<>();
    private List<String> imgNameList = new ArrayList<>();

    @BeforeEach
    public void testCont() {
//
//
//        formData.append("meetUpDesc", e.target.meetUpDesc.value);
//        formData.append("interestSeq", e.target.interestCategory.value); // 키값(interestSeq)을 추가
//        formData.append("meetUpMaxEntry", maxParticipants);
//        formData.append("meetUpDeadLine", meetUpDateTime);


        Users user = new Users();


        Interest interest = new Interest();
        meetUpBoardDetailImg.setMeetUpDetailImgSrc("testSrc");
        meetUpBoardDetailImg.setMeetUpDetailImgType("testType");
        meetUpBoardDetailImg.setMeetUpDetailImgSize("testSize");
        meetUpBoardDetailImg.setMeetUpDetailImgName("testImgName");
        meetUpBoardDetailImgs.add(meetUpBoardDetailImg);

        Date date = new Date();

        ChattingRoom chattingRoom = new ChattingRoom();

        MeetUpBoard meetUpBoard = MeetUpBoard.builder()
                .user(user)
                .meetUpName("testName")
                .meetUpDesc("testDesc")
                .meetUpBoardDetailImgList(meetUpBoardDetailImgs) // 이미지 리스트 설정
                .interest(interest)
                .meetUpPwd(1234)
                .meetUpDeadLine(date)
                .nowEntry(0)
                .meetUpMaxEntry(1)
                .meetUpStatus(0)
                .chattingroom(chattingRoom)
                .build();
    }

    @Test
    void 소모임_생성_테스트() throws Exception {
        MeetUpBoardDTO meetUpBoardDTO = MeetUpBoardDTO.builder()
                //Long타입에는 L을 붙이세요
                .userSeq(userSeq)
                .meetUpName("testName")
                .meetUpDesc("testDesc")
                .interestSeq(interestSeq)
                .meetUpMaxEntry(5)
                .nowEntry(1)
                .meetUpDeadLine(date)
                .build();
        meetUpBoardService.createParty(meetUpBoardDTO, files);
    }

    @Test
    void 소모임_수정_테스트() throws Exception {

        MeetUpUpdateDTO meetUpUpdateDTO = MeetUpUpdateDTO
                .builder()
                .meetUpSeq(1L)
                .meetUpName("testName")
                .meetUpDesc("testDesc")
                .meetUpDeadLine(date)
                .meetUpMaxEntry(5)
                .nowEntry(1)
                .interestSeq(1L)
                .build();

        meetUpBoardService.updateBoard(meetUpUpdateDTO, files);
    }

    @Test
    void 소모임모든리스트_호출() {

        List<MeetUpBoard> meetUpBoardList = meetUpBoardService.selectAll();
        List<MeetUpSendDTO> meetUpSendDTOList = new ArrayList<>();

        for (MeetUpBoard board : meetUpBoardList) {
            List<String> imgNameList = new ArrayList<>();
            MeetUpSendDTO meetUpSendDTO = MeetUpSendDTO.builder()
                    .meetUpSeq(board.getMeetUpSeq())
                    .meetUpDesc(board.getMeetUpDesc())
                    .userSeq(board.getUser().getUserSeq())
                    .interestCate(board.getInterest().getInterestCategory())
                    .meetUpName(board.getMeetUpName())
                    .meetUpBoardDetailImgNameList(imgNameList)
                    .meetUpDeadLine(date)
                    .meetUpMaxEntry(board.getMeetUpMaxEntry())
                    .meetUpPwd(board.getMeetUpPwd())
                    .meetUpStatus(board.getMeetUpStatus())
                    .build();

            meetUpSendDTOList.add(meetUpSendDTO);

            log.info("meetUpSendDTOList: {}", meetUpSendDTOList);
        }
    }

    @Test
    void 유저시퀀스로_소모임찾기(){
        List<MeetUpBoard> meetUpBoadList=meetUpBoardService.findMeetUpByUserSeq(userSeq);
        List<MeetUpSendDTO> meetUpSendDTOList = new ArrayList<>();
        for (MeetUpBoard meetUpBoard : meetUpBoadList) {

            System.out.println("meetUpBoard : " + meetUpBoard);

            MeetUpSendDTO meetUpSendDTO = MeetUpSendDTO.builder()
                    .meetUpSeq(meetUpBoard.getMeetUpSeq())
                    .userSeq(meetUpBoard.getUser().getUserSeq())
                    .meetUpName(meetUpBoard.getMeetUpName())
                    .meetUpRegDate(String.valueOf(meetUpBoard.getMeetUpRegDate()))
                    .nowEntry(meetUpBoard.getNowEntry())
                    .roomId(meetUpBoard.getChattingroom().getRoomId())
                    .chattingRoomSeq(meetUpBoard.getChattingroom().getChattingroomSeq())
                    .meetUpMaxEntry(meetUpBoard.getMeetUpMaxEntry())
                    .meetUpDeadLine(String.valueOf(meetUpBoard.getMeetUpDeadLine()))
                    .build();
            meetUpSendDTOList.add(meetUpSendDTO);

        }
        log.info("userSeq + meetUpSendDTOList: {}", userSeq, meetUpSendDTOList);

    }

}
