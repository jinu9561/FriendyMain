package web.mvc.controller.generalBoard;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.generalBoard.PhotoBoardDTO;
import web.mvc.service.generalBoard.PhotoBoardService;


@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
@Slf4j
public class PhotoBoardController {

    private final PhotoBoardService photoBoardService;

    // 게시글 전체 조회
    @GetMapping("/")
    public ResponseEntity<?> getAllPhotoBoardByDefault() {
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.getAllPhotoBoardByDefault());
    }

    // 게시글 좋아요순 조회
    @GetMapping("/like")
    public ResponseEntity<?> getAllPhotoBoardByLike() {
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.getAllPhotoBoardByLike());
    }

    // 게시글 최신 등록순 조회
    @GetMapping("/regDate")
    public ResponseEntity<?> getAllPhotoBoardByRegDate() {
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.getAllPhotoBoardByRegDate());
    }

    // 게시글 최근 수정순 조회
    @GetMapping("/update")
    public ResponseEntity<?> getAllPhotoBoardByUpdate() {
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.getAllPhotoBoardByUpdate());
    }

    // 내가 쓴 게시글 조회
    @GetMapping("/user/{userSeq}")
    public ResponseEntity<?> getAllPhotoBoardByUser(@PathVariable Long userSeq) {
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.getAllPhotoBoardByUser(userSeq));
    }

    // 관심사 기반 게시글 조회
    @GetMapping("/interest/{selectInterest}")
    public ResponseEntity<?> getAllPhotoBoardByInterest(@PathVariable String selectInterest) {
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.getAllPhotoBoardByInterest(selectInterest));
    }



    // 해당 게시글 조회
    @GetMapping("/{photoBoardSeq}")
    public ResponseEntity<?> getPhotoBoard(@PathVariable Long photoBoardSeq) {
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.getPhotoBoard(photoBoardSeq));
    }

    // 게시글 등록
    @PostMapping("/")
    public ResponseEntity<?> uploadPhotoBoard(@ModelAttribute PhotoBoardDTO photoBoardDTO, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.uploadPhotoBoard(photoBoardDTO,file));
    }

    // 메인 이미지 수정
    @PutMapping("/main/{photoBoardSeq}")
    public ResponseEntity<?> alterMain(@PathVariable Long photoBoardSeq ,@RequestParam("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.alterMain(photoBoardSeq,file));
    }

    // 세부 사진 등록
    @PostMapping("/detail/{photoBoardSeq}")
    public ResponseEntity<?> uploadDetail(@PathVariable Long photoBoardSeq ,@RequestParam("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.uploadDetail(photoBoardSeq,file));
    }

    // 메인 이미지 가져오기
    @GetMapping("/main/img")
    public ResponseEntity<?> getMainImg(@RequestParam String imgName) {
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.getMainImg(imgName));
    }

    // 세부 이미지 가져오기
    @GetMapping("/detail/img")
    public ResponseEntity<?> getDetailImg(@RequestParam String imgName) {
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.getDetailImg(imgName));
    }

    // 해당 게시글 수정
    @PutMapping("/alert/{photoBoardSeq}")
    public ResponseEntity<?> alterPhotoBoard(@PathVariable Long photoBoardSeq, @RequestBody PhotoBoardDTO photoBoardDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.alterPhotoBoard(photoBoardSeq,photoBoardDTO));
    }


    // 해당 게시글 삭제
    @DeleteMapping("/delete/{photoBoardSeq}")
    public ResponseEntity<?> deletePhotoBoard(@PathVariable Long photoBoardSeq){
        return  ResponseEntity.status(HttpStatus.OK).body(photoBoardService.deletePhotoBoard(photoBoardSeq));
    }

    // 메인 이미지 삭제
    @DeleteMapping("/delete/main/{photoBoardSeq}")
    public ResponseEntity<?> deleteMainlImg(@PathVariable Long photoBoardSeq){
        log.info("메인 사진 삭제");
        return  ResponseEntity.status(HttpStatus.OK).body(photoBoardService.deleteMainlImg(photoBoardSeq));
    }

    // 세부 이미지 삭제
    @DeleteMapping("/delete/detail/{photoDetailImgSeq}")
    public ResponseEntity<?> deleteDetailImg(@PathVariable Long photoDetailImgSeq){
        log.info("세부사진 삭제");
        log.info("photoDetailImgSeq" + photoDetailImgSeq);
        return  ResponseEntity.status(HttpStatus.OK).body(photoBoardService.deleteDetailImg(photoDetailImgSeq));
    }
}
