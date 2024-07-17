package web.mvc.service.users;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.mvc.dto.user.EmailVerificationDTO;
import web.mvc.dto.user.UsersDTO;
import web.mvc.entity.user.UserDetail;
import web.mvc.entity.user.Users;
import web.mvc.enums.users.State;
import web.mvc.repository.report.ReportRepository;
import web.mvc.repository.user.InterestRepository;
import web.mvc.repository.user.ProfileInterestRepository;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.user.UserServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
@Transactional
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private ProfileInterestRepository profileInterestRepository;

    @Autowired
    private ReportRepository reportRepository;

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
                .phone("01012345678")
                .Role("USER")
                .build();

        userDetail.setUser(user);

        userRepository.save(user);
        userSeq = user.getUserSeq();
    }

    @Test
    public void 회원가입_테스트() throws ParseException, MessagingException {
        UsersDTO usersDTO = UsersDTO.builder()
                .userId("newUser")
                .userPwd("newPwd")
                .userName("New User")
                .nickName("NewTester")
                .birth("19950101")
                .address("456 New St")
                .email("new@example.com")
                .phone("01098765432")
                .interestCategory(new ArrayList<>())
                .build();

        String result = userService.registerUser(usersDTO);
        log.info("회원가입_테스트 - 결과 메시지: {}", result);
    }

    @Test
    public void 아이디_중복_체크_테스트() {
        boolean isDuplicate = userService.duplicateIdCheck("testUser");
        log.info("아이디_중복_체크_테스트 - 중복 여부: {}", isDuplicate);
    }

    @Test
    public void 사용자_정보_수정_테스트() {
        UsersDTO usersDTO = UsersDTO.builder()
                .address("New Address")
                .email("new@example.com")
                .phone("01098765432")
                .build();

        String result = userService.alter(userSeq, usersDTO);
        log.info("사용자_정보_수정_테스트 - 결과 메시지: {}", result);
    }

    @Test
    public void 비밀번호_찾기_테스트() throws MessagingException {
        UsersDTO usersDTO = UsersDTO.builder()
                .userId("testUser")
                .email("test@example.com")
                .build();

        String result = userService.findPasswordProve(usersDTO);
        log.info("비밀번호_찾기_테스트 - 결과 메시지: {}", result);
    }

    @Test
    public void 비밀번호_변경_테스트() {
        EmailVerificationDTO emailVerificationDTO = EmailVerificationDTO.builder()
                .userId("testUser")
                .userPwd("newPassword")
                .userPwdCheck("newPassword")
                .build();

        String result = userService.alterPassword(emailVerificationDTO);
        log.info("비밀번호_변경_테스트 - 결과 메시지: {}", result);
    }

    @Test
    public void 회원_탈퇴_테스트() {
        String result = userService.resign(userSeq);
        log.info("회원_탈퇴_테스트 - 결과 메시지: {}", result);
    }

    @Test
    public void 이메일_중복_체크_테스트() {
        boolean isDuplicate = userService.duplicateEmailCheck("test@example.com");
        log.info("이메일_중복_체크_테스트 - 중복 여부: {}", isDuplicate);
    }

    @Test
    public void 전화번호_중복_체크_테스트() {
        boolean isDuplicate = userService.duplicatePhoneCheck("01012345678");
        log.info("전화번호_중복_체크_테스트 - 중복 여부: {}", isDuplicate);
    }

    @Test
    public void 닉네임_중복_체크_테스트() {
        boolean isDuplicate = userService.duplicateNickNameCheck("Tester");
        log.info("닉네임_중복_체크_테스트 - 중복 여부: {}", isDuplicate);
    }

}
