package web.mvc.service.user;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import web.mvc.dto.user.EmailVerificationDTO;
import web.mvc.dto.user.UsersDTO;



public interface UserService {

    // 가입 하기
    public String registerUser(UsersDTO usersDTO) ;

    // id 중복 체크
    public boolean duplicateIdCheck(String userId);

    // 개인 정보 수정하기
    public String alter(Long userSeq,UsersDTO usersDTO);

    // 비밀 번호 찾기 -> 인증 번호 발급 - > 비밀번호 변경
    public String findPasswordProve(UsersDTO usersDTO);

    // 비밀 번호 변경
    public String alterPassword(EmailVerificationDTO emailVerificationDTO);

    // 회원 탈퇴
    public String resign(Long userSeq);

    // 이메일 중복 체크
    public boolean duplicateEmailCheck(String email);


    // 전화번호 중복 체크
    public boolean duplicatePhoneCheck(String phone);


    // 닉네임 중복 체크
    public boolean duplicateNickNameCheck(String nickName);


///////////////////////////////진우가 추가한 코드///////////////////////////////
    String updateReportResult(Long reportId, int result, int newState);
}
