package web.mvc.service.main;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import web.mvc.dto.main.MainMeetupDTO;
import web.mvc.dto.user.UsersDTO;

import java.util.List;

public interface MainService {

    // 관심사로 소모임 추천
    public List<MainMeetupDTO> getMeetupByInterest(@PathVariable UsersDTO usersDTO);


    // 소모임 메인 이미지 불러오기
    @GetMapping("/main/img")
    public Resource getMainImg(@RequestParam String imgName);


    // 소모임 세부 이미지 불러오기
    @GetMapping("/detail/img")
    public Resource getDetailImg(@RequestParam String imgName);

}
