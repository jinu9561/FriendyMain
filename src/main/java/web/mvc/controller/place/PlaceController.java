package web.mvc.controller.place;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.user.UsersDTO;
import web.mvc.entity.user.Users;
import web.mvc.service.place.PlaceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
@Slf4j
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/")
    public ResponseEntity<?> getPlaceByUserAddress(@RequestBody UsersDTO usersDTO){
        log.info("place : "+ usersDTO.toString());
        return ResponseEntity.status(HttpStatus.OK).body(placeService.getPlaceByUserAddress(usersDTO));
    }

    @GetMapping("/main/img")
    public ResponseEntity<?> getMainImg(@RequestParam String imgName) {
        return ResponseEntity.status(HttpStatus.OK).body(placeService.getMainImg(imgName));
    }

    @GetMapping("/detail/img")
    public ResponseEntity<?> getDetailImg(@RequestParam String imgName) {
        return ResponseEntity.status(HttpStatus.OK).body(placeService.getDetailImg(imgName));
    }
}
