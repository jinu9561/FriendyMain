package web.mvc.controller.meetUpBoard;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.meetUpBoard.MeetUpRequestDTO;
import web.mvc.dto.user.UsersDTO;
import web.mvc.entity.meetUpBoard.MeetUpBoard;
import web.mvc.entity.meetUpBoard.MeetUpBoardList;
import web.mvc.entity.meetUpBoard.MeetUpRequest;
import web.mvc.entity.user.Users;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.meetUpBoard.MeetUpBoardListRepository;
import web.mvc.repository.meetUpBoard.MeetUpBoardRepository;
import web.mvc.repository.meetUpBoard.MeetUpRequestRepository;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.meetUpBoard.MeetUpRequestService;
import web.mvc.service.meetUpBoard.MeetUpRequestServiceImpl;

import java.util.*;

@RestController
@RequestMapping("/partyBoard/")
@AllArgsConstructor
public class MeetUpRequestController {

    private final MeetUpRequestService meetUpRequestService;
    private final UserRepository userRepository;
    private final MeetUpRequestServiceImpl meetUpRequestServiceImpl;
    private final MeetUpBoardRepository meetUpBoardRepository;


    @PostMapping(value = "/request", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> meetUpRequest(@ModelAttribute MeetUpRequestDTO meetUpRequestDTO) {

        // 신청 중복 확인
        List<Long> list = meetUpRequestService.checkValidRequest(meetUpRequestDTO.getMeetUpSeq());
        for (Long seq : list) {
            // 이 부분에서 에러를 발생시킵니다.
            if (seq == meetUpRequestDTO.getUserSeq()) {
                // 에러 메시지를 Map에 저장하여 반환
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("message", "이미 신청하셨습니다. 신청을 기다려주세요.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
            }
        }
        int result = meetUpRequestService.createMeetUpRequest(meetUpRequestDTO);
        if(result==1){

        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    //신청창에 신청 리스트 출력.
    //입력받은 소모임 시퀀스로
    @GetMapping("/request/selectBySeq")
    public ResponseEntity<?> meetUpRequestCheck(@RequestParam Long meetUpSeq) {
        List<MeetUpRequest> list = meetUpRequestService.findAllReqestBySeq(meetUpSeq);
        System.out.println("meetupSeq" + meetUpSeq + " || 12345");
        List<MeetUpRequestDTO> requestList = new ArrayList<>();


        for (MeetUpRequest meetUpRequest : list) {
            MeetUpRequestDTO meetUpRequestDTO = MeetUpRequestDTO.builder()
                    .userNickName(meetUpRequest.getUser().getNickName())
                    .userSeq(meetUpRequest.getUser().getUserSeq())
                    .meetUpRequestStatus(meetUpRequest.getMeetUpRequestStatus())
                    .meetUpSeq(meetUpRequest.getMeetUpRequestSeq())
                    .meetUpRequestSeq(meetUpRequest.getMeetUpRequestSeq())
                    .requestText(meetUpRequest.getRequestText())
                    .build();
            requestList.add(meetUpRequestDTO);

        }

        return ResponseEntity.status(HttpStatus.OK).body(requestList);
    }

    @GetMapping("/myRequestList")
    public ResponseEntity<?> myRequestList(@RequestParam long userSeq){


        List<MeetUpRequest> meetUpRequestList = meetUpRequestService.findAllRequestByUserSeq(userSeq);
        System.out.println();
        List<MeetUpRequestDTO> requestDTOList = new ArrayList<>();


        for (MeetUpRequest meetUpRequest : meetUpRequestList) {
            MeetUpRequestDTO meetUpRequestDTO = MeetUpRequestDTO.builder()
                    .meetUpName(meetUpRequest.getMeetUpBoard().getMeetUpName())
                    .userNickName(meetUpRequest.getUser().getNickName())
                    .userSeq(meetUpRequest.getUser().getUserSeq())
                    .meetUpRequestStatus(meetUpRequest.getMeetUpRequestStatus())
                    .meetUpSeq(meetUpRequest.getMeetUpBoard().getMeetUpSeq())
                    .meetUpRequestSeq(meetUpRequest.getMeetUpRequestSeq())
                    .requestText(meetUpRequest.getRequestText())
                    .build();
            requestDTOList.add(meetUpRequestDTO);
            System.out.println("meetUpRequestDTO : +++"+meetUpRequestDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(requestDTOList);
    }



    @PutMapping(value = "/request/changestatus")

    public ResponseEntity<?> changeStatus(@RequestBody MeetUpRequestDTO meetUpRequestDTO) {

        MeetUpBoard meetUpBoard = meetUpBoardRepository.findMeetUpBoardByMeetUpSeq(meetUpRequestDTO.getMeetUpSeq());
        int maxEntry = meetUpBoard.getMeetUpMaxEntry();
        int nowEntry = meetUpBoard.getNowEntry();
        int count = 0;
        System.out.println(maxEntry + "|||" + nowEntry);
        int status = meetUpRequestDTO.getMeetUpRequestStatus();
        Long meetUpSeq = meetUpRequestDTO.getMeetUpSeq();
        Long userSeq = meetUpRequestDTO.getUserSeq();

        System.out.println("insertStatus : "+ status);
        System.out.println("userSeq : " + userSeq);
        System.out.println("metUpSeq :" + meetUpSeq);
        if (status == 1) {
            if (maxEntry > nowEntry) {
                meetUpRequestServiceImpl.addMeetUpList(meetUpRequestDTO);
                System.out.println("여긴가 ? ");
                List<MeetUpBoardList> meetUpBoardList = meetUpBoard.getMeetUpBoardList();
                for (MeetUpBoardList meetUpBoardList1 : meetUpBoardList) {
                        count++;
                }
                meetUpBoardRepository.updateNowCount(count, meetUpRequestDTO.getMeetUpSeq());
                System.out.println("리스트 테스트2" + nowEntry);
                String result2 = meetUpRequestService.updateStatusByReqSeq(status, meetUpSeq, userSeq);
                System.out.println("상태가 변경되었습니다." + "||" + result2);
            } else if (maxEntry <= nowEntry) {
                    throw new GlobalException(ErrorCode.MAX_ENTRY);
            }
        } else if (status == 2) {
            System.out.println("여기까지 오긴함 ?");
            meetUpRequestServiceImpl.deleteFromMeetUp(meetUpSeq, userSeq);
            List<MeetUpBoardList> meetUpBoardList = meetUpBoard.getMeetUpBoardList();
            for (MeetUpBoardList meetUpBoardList1 : meetUpBoardList) {
                count++;
            }
            meetUpBoardRepository.updateNowCount(count, meetUpRequestDTO.getMeetUpSeq());
            meetUpRequestService.updateStatusByReqSeq(status, meetUpSeq, userSeq);

            return ResponseEntity.status(HttpStatus.OK).body(null);


        }


        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    @DeleteMapping(value = "/request/delete")
    public ResponseEntity<?> deleteFromMeetUp(@RequestBody MeetUpRequestDTO meetUpRequestDTO) {
        //삭제 하고자 하는 소모임의(소모임 시퀀스)  참여 명단에서 삭제당하는 유저의 시퀀스를 넘김.
        System.out.println("여기까지는 잘 오냐 ? ");



        meetUpRequestService.deleteFromMeetUp(meetUpRequestDTO.getUserSeq(), meetUpRequestDTO.getMeetUpSeq());
        return null;
    }

    @GetMapping(value = "/request/profile/{userSeq}")
    public ResponseEntity<?> selectProfileBySeq(@PathVariable Long userSeq) {


        System.out.println("userSeq: " + userSeq);
        Optional<Users> usersOptional = userRepository.findById(userSeq);
        UsersDTO usersDTO = new UsersDTO();
        if (usersOptional.isPresent()) {
            Users users = usersOptional.get();
            users.getNickName();
            usersDTO = UsersDTO.builder()
                    .nickName(users.getNickName())
                    .email(users.getEmail())
                    .country(users.getCountry())
                    .gender(users.getGender())
                    .phone(users.getPhone())
                    .build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(usersDTO);

    }

    @PostMapping(value = "/addMeetUpList")
    public ResponseEntity<?> addMeetUpList(@RequestBody MeetUpRequestDTO meetUpRequestDTO) {
        MeetUpBoard meetUpBoard = meetUpBoardRepository.findMeetUpBoardByMeetUpSeq(meetUpRequestDTO.getMeetUpSeq());
        int maxEntry = meetUpBoard.getMeetUpMaxEntry();
        int nowEntry = meetUpBoard.getNowEntry();

        if (nowEntry < maxEntry) {
            meetUpRequestServiceImpl.addMeetUpList(meetUpRequestDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body("test");

    }

    @GetMapping(value = "/findMeetUpList")
    public ResponseEntity<?> findMeetUpList(@RequestParam Long meetUpSeq){

        List<UsersDTO> inviteUserList= meetUpRequestServiceImpl.findMeetUpList(meetUpSeq);


        return  ResponseEntity.status(HttpStatus.OK).body(inviteUserList);
    }

    @DeleteMapping(value = "/request/deletemy")
    public ResponseEntity<?> deleteRequest(@RequestParam Long userSeq , @RequestParam Long meetUpSeq) {

        System.out.println(userSeq + "|| " + meetUpSeq);
        meetUpRequestService.deleteRequest(userSeq, meetUpSeq );
        return null;
    }
    

}