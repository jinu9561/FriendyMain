package web.mvc.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.user.ProFileDetailImgDTO;
import web.mvc.dto.user.ProfileDTO;
import web.mvc.service.user.ProfileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Slf4j
public class ProfileController {

    private final ProfileService profileService;


    @GetMapping("/{userSeq}")
    public ResponseEntity<?> getProfile(@PathVariable("userSeq") Long userSeq) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.getProfile(userSeq));
    }

    @PostMapping("/main/{userSeq}")
    public ResponseEntity<?> uploadProfile(@PathVariable("userSeq") Long userSeq, @RequestParam("mainProfileImage") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.uploadMainPicture(userSeq,file));
    }

    @PostMapping("/detail/{userSeq}")
    public ResponseEntity<?> uploadDetail(@PathVariable("userSeq") Long userSeq, @RequestParam("detailImage") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.uploadDetail(userSeq,file));
    }

    @PutMapping("/alter/{userSeq}")
    public ResponseEntity<?> alterProfile(@PathVariable("userSeq") Long userSeq, @RequestBody ProfileDTO profileDTO) {
        log.info(" "+profileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(profileService.alterProfile(userSeq,profileDTO));
    }

    @GetMapping("/main/img")
    public ResponseEntity<?> getMainImg(@RequestParam String imgName) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.getMainImg(imgName));
    }

    @GetMapping("/detail/img")
    public ResponseEntity<?> getDetailImg(@RequestParam String imgName) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.getDetailImg(imgName));
    }

    @PutMapping("/alter/password/{userSeq}")
    public ResponseEntity<?> alterPassword(@PathVariable("userSeq") Long userSeq, @RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.alterPassword(userSeq,profileDTO));
    }

    @DeleteMapping("/main/img/{userSeq}")
    public ResponseEntity<?> deleteMainImg(@PathVariable("userSeq") Long userSeq) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.deleteMainImg(userSeq));
    }

    @DeleteMapping("/detail/img/{profileDetailImgSeq}")
    public ResponseEntity<?> deleteDetailImg(@PathVariable Long profileDetailImgSeq) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.deleteDetailImg(profileDetailImgSeq));
    }


}
