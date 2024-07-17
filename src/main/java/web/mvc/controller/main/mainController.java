package web.mvc.controller.main;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.user.UsersDTO;
import web.mvc.service.main.MainService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main/meetup")
@Slf4j
public class mainController {

    private final MainService mainService;

    // 관심사로 소모임 추천
    @PostMapping("/")
    public ResponseEntity<?> getMeetupByInterest(@RequestBody UsersDTO usersDTO){
        log.info("메인 관심사로 소모임");
        return ResponseEntity.status(HttpStatus.OK).body(mainService.getMeetupByInterest(usersDTO));
    }

    // 소모임 메인 이미지 불러오기
    @GetMapping("/main/img")
    public ResponseEntity<?> getMainImg(@RequestParam String imgName) {
        return ResponseEntity.status(HttpStatus.OK).body(mainService.getMainImg(imgName));
    }

    // 소모임 세부 이미지 불러오기
    @GetMapping("/detail/img")
    public ResponseEntity<?> getDetailImg(@RequestParam String imgName) {
        return ResponseEntity.status(HttpStatus.OK).body(mainService.getDetailImg(imgName));
    }
}
