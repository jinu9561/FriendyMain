package web.mvc.service.users;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.mvc.dto.user.ProfileDTO;
import web.mvc.entity.user.Profile;
import web.mvc.entity.user.UserDetail;
import web.mvc.entity.user.Users;
import web.mvc.enums.users.ImgStatus;
import web.mvc.repository.user.ProfileDetailImgRepository;
import web.mvc.repository.user.ProfileRepository;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.common.CommonService;
import web.mvc.service.user.ProfileServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@SpringBootTest
@Transactional
@Slf4j
public class ProfileServiceTest {

    @Autowired
    private ProfileServiceImpl profileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileDetailImgRepository profileDetailImgRepository;

    @Autowired
    private CommonService commonService;

    private Long userSeq;

    @BeforeEach
    public void setUp() {
        UserDetail userDetail = new UserDetail();
        userDetail.setUserJelly(0);

        Users user = Users.builder()
                .userDetail(userDetail)
                .userId("testUser")
                .userPwd("testPwd")
                .userName("Test User")
                .nickName("Tester")
                .birth(new Date())
                .address("123 Test St")
                .email("test@example.com")
                .phone("123-456-7890")
                .Role("USER")
                .jellyTransactionList(new ArrayList<>())
                .build();

        userDetail.setUser(user);

        userRepository.save(user);
        userSeq = user.getUserSeq();

        Profile profile = Profile.builder()
                .user(user)
                .profileMainImgName("mainImage.jpg")
                .profileMainImgSrc("path/to/mainImage")
                .profileMainImgType("image/jpeg")
                .profileMainImgSize("500KB")
                .imgStatus(ImgStatus.APPROVED)
                .profileDetailImgList(new ArrayList<>())
                .build();

        profileRepository.save(profile);
    }

    @Test
    public void 프로필_가져오기_테스트() {
        Map<String, Object> profileData = profileService.getProfile(userSeq);
        log.info("프로필_가져오기_테스트 - 프로필 데이터: {}", profileData);
    }

    @Test
    public void 프로필_수정_테스트() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setIntroduce("New introduction");
        profileDTO.setAddress("New address");
        profileDTO.setPhone("987-654-3210");
        profileDTO.setEmail("new@example.com");
        profileDTO.setNickName("NewTester");

        // interestCategory가 null일 경우를 대비하여 null로 설정
        profileDTO.setInterestCategory(new ArrayList<>());

        String result = profileService.alterProfile(userSeq, profileDTO);
        log.info("프로필_수정_테스트 - 결과 메시지: {}", result);
    }

    @Test
    public void 비밀번호_수정_테스트() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUserPwd("newPassword");
        profileDTO.setUserPwdCheck("newPassword");

        String result = profileService.alterPassword(userSeq, profileDTO);
        log.info("비밀번호_수정_테스트 - 결과 메시지: {}", result);
    }

    @Test
    public void 메인_이미지_삭제_테스트() {
        String result = profileService.deleteMainImg(userSeq);
        log.info("메인_이미지_삭제_테스트 - 결과 메시지: {}", result);
    }

    @Test
    public void 상세_이미지_삭제_테스트() {
        Profile profile = profileRepository.findByUserSeq(userSeq).orElseThrow(() -> new RuntimeException("Profile not found"));

        if (profile.getProfileDetailImgList().isEmpty()) {
            log.info("상세_이미지_삭제_테스트 - Profile has no detail images to delete.");
            return;
        }

        Long profileDetailImgSeq = profile.getProfileDetailImgList().get(0).getProfileDetailImgSeq();

        String result = profileService.deleteDetailImg(profileDetailImgSeq);
        log.info("상세_이미지_삭제_테스트 - 결과 메시지: {}", result);
    }
}
