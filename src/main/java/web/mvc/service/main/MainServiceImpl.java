package web.mvc.service.main;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import web.mvc.dto.main.MainMeetupDTO;
import web.mvc.dto.user.UsersDTO;
import web.mvc.entity.meetUpBoard.MeetUpBoard;
import web.mvc.entity.meetUpBoard.MeetUpBoardDetailImg;
import web.mvc.entity.user.Interest;
import web.mvc.entity.user.ProfileInterest;
import web.mvc.entity.user.Users;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.meetUpBoard.MeetUpBoardRepository;
import web.mvc.repository.user.InterestRepository;
import web.mvc.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MainServiceImpl implements MainService {
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final MeetUpBoardRepository meetUpBoardRepository;

    @Value("${meetUp.save.dir}")
    private String uploadDir;

    @Override
    public List<MainMeetupDTO> getMeetupByInterest(UsersDTO usersDTO) {

        log.info("소모임 userID" + usersDTO.getUserId());
        Users user = userRepository.findUserByUserId(usersDTO.getUserId());
        Interest interest = null;
        if(user != null){
            log.info("소모임 user "+ user);
            List<ProfileInterest> profileInterestList = user.getProfile().getProfileInterestList();
            interest = profileInterestList.get(0).getInterest();
        }else{
            log.info("소모임 user "+ user);
            interest = interestRepository.findById(1L).orElseThrow(()-> new GlobalException(ErrorCode.ACCESS_DENIED));
            log.info("interest "+ interest.getInterestCategory());
        }

        List<MeetUpBoard> meetUpBoardList = meetUpBoardRepository.findMeetUpBoardByInterest(interest.getInterestCategory());
        List<MainMeetupDTO> mainMeetupDTOList = new ArrayList<>();

        for(MeetUpBoard meetUpBoard : meetUpBoardList){
            List<String> meetupImageList = this.getMeetupImage(meetUpBoard.getMeetUpBoardDetailImgList());

            MainMeetupDTO mainMeetupDTO = MainMeetupDTO.builder()
                    .meetUpSeq(meetUpBoard.getMeetUpSeq())
                    .userSeq(meetUpBoard.getUser().getUserSeq())
                    .meetUpName(meetUpBoard.getMeetUpName())
                    .meetUpDesc(meetUpBoard.getMeetUpDesc())
                    .interestSeq(interest.getInterestSeq())
                    .meetUpDeadLine(meetUpBoard.getMeetUpDeadLine())
                    .meetUpDetailImgName(meetupImageList)
                    .meetUpMaxEntry(meetUpBoard.getMeetUpMaxEntry())
                    .meetUpStatus(meetUpBoard.getMeetUpStatus())
                    .build();
            mainMeetupDTOList.add(mainMeetupDTO);
        }

        return mainMeetupDTOList;
    }

    public List<String> getMeetupImage(List<MeetUpBoardDetailImg> list){
        List<String> result = new ArrayList<>();
        for(MeetUpBoardDetailImg meetUpBoardDetailImg : list){
            result.add(meetUpBoardDetailImg.getMeetUpDetailImgName());
        }
        return result;
    }

    @Override
    public Resource getMainImg(String imgName) {
        Resource resource = new FileSystemResource(uploadDir+"\\"+imgName);
        return resource;
    }

    @Override
    public Resource getDetailImg(String imgName) {
        Resource resource = new FileSystemResource(uploadDir+"/detail"+"\\"+imgName);
        return resource;
    }
}
