package web.mvc.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.place.PlaceRecommendationDTO;
import web.mvc.service.admin.AdminPlaceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/place")
@Slf4j
public class AdminPlaceController {

    private final AdminPlaceService adminPlaceService;

    @GetMapping("/")
    public ResponseEntity<?> getPlaceList(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPlaceService.getPlaceList());
    }

    @GetMapping("/regdate")
    public ResponseEntity<?> getPlaceListByRegDate(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPlaceService.getPlaceListByRegDate());
    }

    @GetMapping("/update")
    public ResponseEntity<?> getPlaceListByUpdate(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPlaceService.getPlaceListByUpdate());
    }

    @PostMapping(value="/upload")
    public ResponseEntity<?> uploadePlace(@ModelAttribute PlaceRecommendationDTO placeRecommendationDTO,@RequestParam("file") MultipartFile file ){
        return ResponseEntity.status(HttpStatus.OK).body(adminPlaceService.uploadePlace(placeRecommendationDTO,file));
    }

    @PutMapping("/main/{placeSeq}")
    public ResponseEntity<?> uploadMainImg(@PathVariable Long placeSeq ,@RequestParam("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(adminPlaceService.uploadMainImg(placeSeq,file));
    }

    @PostMapping("/detail/{placeSeq}")
    public ResponseEntity<?> uploadPlaceDetail(@PathVariable Long placeSeq ,@RequestParam("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(adminPlaceService.uploadPlaceDetail(placeSeq,file));
    }

    @PutMapping("/alter/{placeSeq}")
    public ResponseEntity<?> alterPlace(@PathVariable Long placeSeq, @RequestBody PlaceRecommendationDTO placeRecommendationDTO){
        return ResponseEntity.status(HttpStatus.OK).body(adminPlaceService.alterPlace(placeSeq,placeRecommendationDTO));
    }

    @DeleteMapping("/delete/{placeSeq}")
    public ResponseEntity<?> deletePlace(@PathVariable Long placeSeq){
        log.info("전체 삭제");
        return  ResponseEntity.status(HttpStatus.OK).body(adminPlaceService.deletePlace(placeSeq));
    }

    @DeleteMapping("/delete/main/{placeSeq}")
    public ResponseEntity<?> deleteMainlImg(@PathVariable Long placeSeq){
        return  ResponseEntity.status(HttpStatus.OK).body(adminPlaceService.deleteMainlImg(placeSeq));
    }

    @DeleteMapping("/delete/detail/{placeDetailImgSeq}")
    public ResponseEntity<?> deleteDetailImg(@PathVariable Long placeDetailImgSeq){
        return  ResponseEntity.status(HttpStatus.OK).body(adminPlaceService.deleteDetailImg(placeDetailImgSeq));
    }

    @GetMapping("/{placeSeq}")
    public ResponseEntity<?> getPlace(@PathVariable Long placeSeq){
        return ResponseEntity.status(HttpStatus.OK).body(adminPlaceService.getPlace(placeSeq));
    }

}
