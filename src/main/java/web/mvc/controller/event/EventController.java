package web.mvc.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.event.EventDTO;
import web.mvc.dto.event.EventDetailImgDTO;
import web.mvc.service.event.EventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
@Slf4j
public class EventController {

    private final EventService eventService;

    //전체검색(데드라인 순으로)
    @GetMapping("/eventlistdead")
    public ResponseEntity<?> getAllEventAdminList(){
        List<EventDTO> events = eventService.selectAllByDeadlLine();
        return ResponseEntity.ok(events);
    }

    //전체검색(등록일 순으로)
    @GetMapping("/eventlist")
    public ResponseEntity<?> getAllEventList(){
        List<EventDTO> events = eventService.selectAllByRegDate();
        return ResponseEntity.ok(events);
    }

    //이벤트 1개 상세검색(eventSeq로 - 배너에서 사용)
    @GetMapping("/detail/{eventSeq}")
    public List<EventDTO> getEventByEventSeq(@PathVariable Long eventSeq){

        return eventService.selectEventByEventSeq(eventSeq);
    }

    //배너창 세부 이벤트 사진 가져오기
    @PutMapping("/picture/{eventDetailImgSeq}")
    public ResponseEntity<?> getEventDetailImg(@PathVariable Long eventDetailImgSeq){
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventDetailImg(eventDetailImgSeq));
    }

}
