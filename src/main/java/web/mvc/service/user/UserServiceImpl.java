package web.mvc.service.user;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.mvc.dto.user.AdminDTO;
import web.mvc.dto.user.EmailVerificationDTO;
import web.mvc.dto.user.UsersDTO;
import web.mvc.entity.report.Report;
import web.mvc.entity.user.*;
import web.mvc.enums.users.State;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.report.ReportRepository;
import web.mvc.repository.user.InterestRepository;
import web.mvc.repository.user.ProfileInterestRepository;
import web.mvc.repository.user.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailVerificationService emailVerificationService;
    private final InterestRepository interestRepository;
    private final ProfileInterestRepository profileInterestRepository;

    private final AdminDTO adminDTO;
    private String subject ="Friendy 가입 인증코드 입니다!";
    private String failMsg = "메일 발송을 실패했습니다";
    private String registerMsg = "해당 이메일로 인증 코드를 보냈습니다 확인해 주세요";
    private String updateMsg ="수정에 성공했습니다";
    private String findPasswordMsg = "해당 이메일로 비밀번호 변경 확인 코드를 보냈습니다 확인해 주세요";
    private String findSubject = "Friendy 회원님의 비밀번호 변경 확인 입니다.";
    private String findContent = "회원님의 비밀번호 변경 확인 코드 입니다. : {code}";
    private String alertMsg = "변경이 완료되었습니다";
    private String alertFailMsg = "비밀번호가 일치 하지않습니다";
    private String resignMsg ="탈퇴를 완료했습니다";

    @EventListener(ApplicationReadyEvent.class)
    public void handleApplicationReadyEvent(ApplicationReadyEvent event) {
        log.info("Application ready");
        log.info("adminDTO: " + adminDTO);
        if(userRepository.findUserByUserId(adminDTO.getUserId())==null) {
            //생년월일 date형식으로 바꿔기
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            // 잘못된 날짜를 자동으로 유효한 날짜로 변환하는 기능 비활성화 -> 잘못된 날짜는 바로 예외가 나와야 되기 때문에
            formatter.setLenient(false);
            Date birth = null;

            try {
                birth = formatter.parse(adminDTO.getBirth());
            } catch (ParseException e) {
                throw new GlobalException(ErrorCode.WRONG_DATE);
            }

            String encodePwd  = passwordEncoder.encode(adminDTO.getUserPwd());

            Users admin = Users.builder()
                    .userId(adminDTO.getUserId())
                    .userName(adminDTO.getUserName())
                    .userPwd(encodePwd)
                    .nickName(adminDTO.getNickName())
                    .birth(birth)
                    .address(adminDTO.getAddress())
                    .email(adminDTO.getEmail())
                    .phone(adminDTO.getPhone())
                    .country(adminDTO.getCountry())
                    .gender(adminDTO.getGender())
                    .Role("ROLE_ADMIN")
                    .build();

            // 저장하기 전에 UserDetail, profile 생성
            UserDetail userDetail = new UserDetail(admin);
            Profile profile = new Profile(admin);
            userDetail.setUserState(State.CERTIFIED);

            // user에 userDetail, profile  저장
            admin.setUserDetail(userDetail);
            admin.setProfile(profile);


            Users savedAdmin = userRepository.save(admin);
        }
    }

//
    @Override
    public String registerUser(UsersDTO usersDTO) {

        //생년월일 date형식으로 바꿔기
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        // 잘못된 날짜를 자동으로 유효한 날짜로 변환하는 기능 비활성화 -> 잘못된 날짜는 바로 예외가 나와야 되기 때문에
        formatter.setLenient(false);
        Date birth = null;

        // 정규 표현식을 사용하여 한글 문자가 포함되어 있는지 확인
        Pattern koreanPattern = Pattern.compile("[ㄱ-ㅎㅏ-ㅣ가-힣]");
        if (koreanPattern.matcher(usersDTO.getUserId()).find()) {
            throw new GlobalException(ErrorCode.INVALID_USER_ID);
        }

        // 생년월일 형식 검사
        Pattern birthPattern = Pattern.compile("\\d{4}\\d{2}\\d{2}");
        if (!birthPattern.matcher(usersDTO.getBirth()).matches()) {
            throw new GlobalException(ErrorCode.INVALID_BIRTH_DATE);
        }

        // 전화번호 형식 검사 (예: 01012345678)
        Pattern phonePattern = Pattern.compile("^01[0-9]\\d{3,4}\\d{4}$");
        if (!phonePattern.matcher(usersDTO.getPhone()).matches()) {
            throw new GlobalException(ErrorCode.INVALID_PHONE_NUMBER);
        }

        try {
            birth = formatter.parse(usersDTO.getBirth());

            if(userRepository.findUserByUserId(usersDTO.getUserId()) != null){
                //해당 아이디 존재
                throw new GlobalException(ErrorCode.FIND_ID);
            }

            // 비번 암호화
            String encodePwd  = passwordEncoder.encode(usersDTO.getUserPwd());

            Users user = Users.builder()
                    .userId(usersDTO.getUserId())
                    .userName(usersDTO.getUserName())
                    .userPwd(encodePwd)
                    .nickName(usersDTO.getNickName())
                    .birth(birth)
                    .address(usersDTO.getAddress())
                    .email(usersDTO.getEmail())
                    .phone(usersDTO.getPhone())
                    .country(usersDTO.getCountry())
                    .gender(usersDTO.getGender())
                    .Role("인증 미완료")
                    .build();

            // 저장하기 전에 UserDetail, profile 생성
            UserDetail userDetail = new UserDetail(user);
            Profile profile = new Profile(user);
            userDetail.setUserState(State.WAITING);

            // user에 userDetail, profile  저장
            user.setUserDetail(userDetail);
            user.setProfile(profile);

            // user에 관심사 저장
            List<String> interests = usersDTO.getInterestCategory();
            Profile savedProfile = user.getProfile();

            for(String i : interests){
                Interest interest = interestRepository.findByInterestCategory(i);
                ProfileInterest saveInterest = new ProfileInterest(interest,savedProfile);
                savedProfile.getProfileInterestList().add(saveInterest);
            }

            // db에 유저 저장
            Users savedUsers = userRepository.save(user);


            // 이메일 인증을 위한 emailVerification 저장
            EmailVerification savedEmailVerification = emailVerificationService.saveEmailToken(savedUsers);
            // 이메일 인증토큰 보내기
            String content ="다음 인증 코드를 사용하여 회원가입을 완료하세요: {code}";
            content = content.replace("{code}", savedEmailVerification.getEmailToken());
            log.info("이메일 인증 번호 : "+ savedEmailVerification.getEmailToken());
            emailVerificationService.sendEmailVerificationCode(savedUsers.getEmail(),subject,content);

            log.info("savedUsers = {}", savedUsers);


        } catch (ParseException e) {
            throw new GlobalException(ErrorCode.WRONG_DATE);
        } catch (MessagingException m){
            return failMsg;
        }

        return registerMsg;
    }

    @Override
    public boolean duplicateIdCheck(String userId) {
        if(userRepository.findUserByUserId(userId)!= null){
            // 해당 id가 있다
            return  true;
        }
        
        return false;
    }

    @Override
    public String alter(Long userSeq,UsersDTO usersDTO) {
        Users user = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));

        String newPwd = passwordEncoder.encode(user.getUserPwd());

        user.setAddress(usersDTO.getAddress());
        user.setEmail(usersDTO.getEmail());
        user.setPhone(usersDTO.getPhone());
        user.setUserPwd(newPwd);

        return updateMsg;
    }

    @Override
    public String findPasswordProve(UsersDTO usersDTO) {
        Users user = userRepository.findUserByUserId(usersDTO.getUserId());
        if(user==null){
            throw new GlobalException(ErrorCode.NOTFOUND_ID);
        }

        findContent = findContent.replace("{code}", user.getUserPwd());
        try{
            emailVerificationService.sendEmailVerificationCode(usersDTO.getEmail(),findSubject,findContent);
        }catch (MessagingException m){
            return failMsg;
        }


        return findPasswordMsg;
    }

    @Override
    public String alterPassword(EmailVerificationDTO emailVerificationDTO) {

       Users user = userRepository.findUserByUserId(emailVerificationDTO.getUserId());

       if(user==null){
           throw new GlobalException(ErrorCode.NOTFOUND_ID);
       }

       if(!emailVerificationDTO.getUserPwd().equals(emailVerificationDTO.getUserPwdCheck())){
           return alertFailMsg;
       }

        log.info("alterPassword : " + emailVerificationDTO.getUserPwd());

        // 새 비밀번호 암호화
        String encodePwd  = passwordEncoder.encode(emailVerificationDTO.getUserPwd());
        user.setUserPwd(encodePwd);

        return alertMsg;
    }

    @Override
    public String resign(Long userSeq) {
        Users user = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));
        user.getUserDetail().setUserState(State.TEMPORARY_STOP);
        return resignMsg;
    }

    @Override
    public boolean duplicateEmailCheck(String email) {
        if(userRepository.findUserByEmail(email)!= null){
            // 해당 id가 있다
            return  true;
        }

        return false;
    }

    @Override
    public boolean duplicatePhoneCheck(String phone) {
        if(userRepository.findUserByPhone(phone)!= null){
            // 해당 id가 있다
            return  true;
        }

        return false;
    }

    @Override
    public boolean duplicateNickNameCheck(String nickName) {
        if(userRepository.findUserByNickName(nickName)!= null){
            // 해당 id가 있다
            return  true;
        }

        return false;
    }

///////////////////////////////진우가 추가한 코드//////////////////////////////////////////
    
    private final ReportRepository reportRepository;
    
    @Override
    public String updateReportResult(Long reportSeq, int result, int newState) {
        Report report = reportRepository.findById(reportSeq)
                .orElseThrow(() -> new GlobalException(ErrorCode.INVALID_USER_ID));
    
        Users user = userRepository.findById(report.getReportSeq())
                .orElseThrow(() -> new GlobalException(ErrorCode.INVALID_USER_ID));
    
        // Update report result
        report.setReportResult(result);
        reportRepository.save(report);
    
        // Update user state
        user.getUserDetail().setUserState(State.values()[newState]);
        userRepository.save(user);
    
        return "유저 상태 업데이트 완료";
    }
}
