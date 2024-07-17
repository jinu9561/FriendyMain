package web.mvc.service.user;

import org.springframework.web.bind.annotation.RequestBody;
import web.mvc.dto.user.InterestDTO;
import web.mvc.dto.user.UsersDTO;

import java.util.List;

public interface InterestService {

    // 관심사 리스트 가져오기
    public List<InterestDTO> getInterest();

    // 관심사 수정
    public String alterInterest(Long userSeq,UsersDTO usersDTO);
}
