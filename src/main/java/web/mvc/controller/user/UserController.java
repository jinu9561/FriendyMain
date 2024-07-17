package web.mvc.controller.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.user.EmailVerificationDTO;
import web.mvc.dto.user.UsersDTO;
import web.mvc.service.user.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/logout")
    public ResponseEntity<?> logoutMsg(){
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 완료");
    }

    @PostMapping("/join")
    public ResponseEntity<?> register(@RequestBody UsersDTO usersDTO) {
        log.info("Registering user: {}", usersDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(usersDTO));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> duplicateIdCheck(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.duplicateIdCheck(userId));
    }

    @PutMapping("/alter/{userSeq}")
    public ResponseEntity<?> alter(@PathVariable Long userSeq, @RequestBody UsersDTO usersDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.alter(userSeq,usersDTO));
    }

    @PostMapping("/password")
    public ResponseEntity<?> findPasswordProve(@RequestBody UsersDTO usersDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findPasswordProve(usersDTO));
    }

    @PostMapping("/alter/password")
    public ResponseEntity<?> alterPassword(@RequestBody EmailVerificationDTO emailVerificationDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.alterPassword(emailVerificationDTO));
    }

    @GetMapping("/resign/{userSeq}")
    public ResponseEntity<?> resign(@PathVariable Long userSeq){
        return ResponseEntity.status(HttpStatus.OK).body(userService.resign(userSeq));
    }

    @PostMapping("/email/{email}")
    public ResponseEntity<?> duplicateEmailCheck(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(userService.duplicateEmailCheck(email));
    }

    @PostMapping("/phone/{phone}")
    public ResponseEntity<?> duplicatePhoneCheck(@PathVariable String phone){
        return ResponseEntity.status(HttpStatus.OK).body(userService.duplicatePhoneCheck(phone));
    }

    @PostMapping("/nickName/{nickName}")
    public ResponseEntity<?> duplicateNickNameCheck(@PathVariable String nickName){
        return ResponseEntity.status(HttpStatus.OK).body(userService.duplicateNickNameCheck(nickName));
    }

}
