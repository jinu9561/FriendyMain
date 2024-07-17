package web.mvc.controller.admin;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.service.admin.AdminPhotoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/photo")
@Slf4j
public class AdminPhotoController {

    private final AdminPhotoService adminPhotoService;

    // 전체 조회
    @GetMapping("/")
    public ResponseEntity<?> getAllPhotoBoardByDefault(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPhotoService.getAllPhotoBoardByDefault());
    }

    // 게시글 최신 등록순 조회
    @GetMapping("/regDate")
    public ResponseEntity<?> getAllPhotoBoardByRegDate() {
        return ResponseEntity.status(HttpStatus.OK).body(adminPhotoService.getAllPhotoBoardByRegDate());
    }

    // 게시글 최근 수정순 조회
    @GetMapping("/update")
    public ResponseEntity<?> getAllPhotoBoardByUpdate() {
        return ResponseEntity.status(HttpStatus.OK).body(adminPhotoService.getAllPhotoBoardByUpdate());
    }

    // 해당 게시글 삭제
    @DeleteMapping("/delete/{photoBoardSeq}")
    public ResponseEntity<?> deletePhotoBoard(@PathVariable Long photoBoardSeq){
        return  ResponseEntity.status(HttpStatus.OK).body(adminPhotoService.deletePhotoBoard(photoBoardSeq));
    }

}
