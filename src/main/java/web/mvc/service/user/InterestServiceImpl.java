package web.mvc.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import web.mvc.dto.user.InterestDTO;
import web.mvc.dto.user.UsersDTO;
import web.mvc.entity.user.Interest;
import web.mvc.entity.user.Profile;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.user.InterestRepository;
import web.mvc.repository.user.ProfileRepository;
import web.mvc.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InterestServiceImpl implements InterestService {

    private final InterestRepository interestRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;


    private String alterMsg ="등록 완료 됬습니다";

    @EventListener(ApplicationReadyEvent.class)
    public void init() {


        interestRepository.save(Interest.builder().interestSeq(1L).interestCategory("운동").build());
        interestRepository.save(Interest.builder().interestSeq(2L).interestCategory("독서").build());
        interestRepository.save(Interest.builder().interestSeq(3L).interestCategory("영화").build());
        interestRepository.save(Interest.builder().interestSeq(4L).interestCategory("요리").build());
        interestRepository.save(Interest.builder().interestSeq(5L).interestCategory("여행").build());
        interestRepository.save(Interest.builder().interestSeq(6L).interestCategory("음악").build());
        interestRepository.save(Interest.builder().interestSeq(7L).interestCategory("미술").build());
        interestRepository.save(Interest.builder().interestSeq(8L).interestCategory("촬영").build());
        interestRepository.save(Interest.builder().interestSeq(9L).interestCategory("쇼핑").build());
        interestRepository.save(Interest.builder().interestSeq(10L).interestCategory("명상").build());
        interestRepository.save(Interest.builder().interestSeq(11L).interestCategory("반려 동물").build());
        interestRepository.save(Interest.builder().interestSeq(12L).interestCategory("노래").build());
        interestRepository.save(Interest.builder().interestSeq(13L).interestCategory("에어소프트").build());
        interestRepository.save(Interest.builder().interestSeq(14L).interestCategory("천체관측").build());
        interestRepository.save(Interest.builder().interestSeq(15L).interestCategory("공예").build());
        interestRepository.save(Interest.builder().interestSeq(16L).interestCategory("게임").build());
        interestRepository.save(Interest.builder().interestSeq(17L).interestCategory("테이스팅").build());
        interestRepository.save(Interest.builder().interestSeq(18L).interestCategory("관람").build());
        interestRepository.save(Interest.builder().interestSeq(19L).interestCategory("낚시").build());
        interestRepository.save(Interest.builder().interestSeq(20L).interestCategory("사냥").build());
        interestRepository.save(Interest.builder().interestSeq(21L).interestCategory("식물 가꾸기").build());
        interestRepository.save(Interest.builder().interestSeq(22L).interestCategory("역사 탐방").build());
        interestRepository.save(Interest.builder().interestSeq(23L).interestCategory("모형 제작").build());
        interestRepository.save(Interest.builder().interestSeq(24L).interestCategory("자동차 튜닝").build());
        interestRepository.save(Interest.builder().interestSeq(25L).interestCategory("드론 비행").build());




    }

    @Override
    public List<InterestDTO> getInterest() {
        List<InterestDTO> initiInterestList = new ArrayList<>();
        List<Interest> interestList = interestRepository.findAll();

        for(Interest i : interestList){
            InterestDTO dto = InterestDTO.builder()
                    .interestSeq(i.getInterestSeq())
                    .interestCategory(i.getInterestCategory())
                    .build();
            initiInterestList.add(dto);
        }

        return initiInterestList;
    }

    @Override
    public String alterInterest(Long userSeq, UsersDTO usersDTO) {

        Profile profile = profileRepository.findByUserSeq(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PROFILE));

//        List<Interest> interestList = interestRepository.findByProfileSeq(profile.getProfileSeq());
//        List<String> interestCategoryList = usersDTO.getInterestCategory();
//
//        for(int i = 0; i< interestList.size(); i++) {
//            String interestCategory = interestCategoryList.get(i);
//            Interest interest = interestList.get(i);
//            interest.setInterestCategory(interestCategory);
//        }
//
//        interestRepository.saveAll(interestList);

        return alterMsg;
    }
}
