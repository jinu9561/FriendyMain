package web.mvc.controller.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.user.UsersDTO;
import web.mvc.entity.user.Interest;
import web.mvc.service.user.InterestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interest")
@Slf4j
public class InterestController {

    private final InterestService interestService;

    @GetMapping("/")
    public ResponseEntity<?> getInterest() {
        log.info("Get interest");
        return ResponseEntity.status(HttpStatus.OK).body(interestService.getInterest());
    }

    @PutMapping("/alter/{userSeq}")
    public ResponseEntity<?> alterInterest(@PathVariable("userSeq")Long userSeq, @RequestBody UsersDTO usersDTO){
        return ResponseEntity.status(HttpStatus.OK).body(interestService.alterInterest(userSeq,usersDTO));
    }
}
