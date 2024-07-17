package web.mvc.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.user.*;
import web.mvc.service.admin.AdminUserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Slf4j
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping("/")
    public ResponseEntity<?> getUsersList(){
        return ResponseEntity.status(HttpStatus.OK).body(adminUserService.getUsersList());
    }

    @PutMapping("/alter/{userSeq}")
    public ResponseEntity<?> alterUserDetail(@PathVariable Long userSeq,@RequestBody UserDetailDTO userDetailDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(adminUserService.alterUserDetail(userSeq,userDetailDTO));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfileList(){
        return ResponseEntity.status(HttpStatus.OK).body(adminUserService.getProfileList());
    }

    @GetMapping("/regDate")
    public ResponseEntity<?> getProfileListByRegDate(){
        return ResponseEntity.status(HttpStatus.OK).body(adminUserService.getProfileListByRegDate());
    }

    @GetMapping("/update")
    public ResponseEntity<?> getProfileListByUpdate(){
        return ResponseEntity.status(HttpStatus.OK).body(adminUserService.getProfileListByUpdate());
    }

    @GetMapping("/lastLogin")
    public ResponseEntity<?> getProfileListByLastLogin(){
        return ResponseEntity.status(HttpStatus.OK).body(adminUserService.getProfileListByLastLogin());
    }

    @GetMapping("/userRate")
    public ResponseEntity<?> getProfileListByUserRate(){
        return ResponseEntity.status(HttpStatus.OK).body(adminUserService.getProfileListByUserRate());
    }

    @GetMapping("/profile/detail/{userSeq}")
    public ResponseEntity<?> getProfileDetail(@PathVariable Long userSeq){
        return ResponseEntity.status(HttpStatus.OK).body(adminUserService.getProfileDetail(userSeq));
    }


    @GetMapping("/profile/{userSeq}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userSeq){
        return ResponseEntity.status(HttpStatus.OK).body(adminUserService.getUserProfile(userSeq));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> alterProfileState(@RequestBody UserProfileDTO userProfileDTO){
        log.info(userProfileDTO.toString());
        return ResponseEntity.status(HttpStatus.OK).body(adminUserService.alterProfileState(userProfileDTO));
    }

    @PutMapping("/profile/detail/{userSeq}")
    public ResponseEntity<?> alterProfileDetailState(@PathVariable Long userSeq,@RequestBody UserProfileDetailDTO userProfileDetailDTO){
        return ResponseEntity.status(HttpStatus.OK).body(adminUserService.alterProfileDetail(userSeq,userProfileDetailDTO));
    }
}
