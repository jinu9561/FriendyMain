package web.mvc.service.admin;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.event.EventDTO;
import web.mvc.dto.event.EventDetailImgDTO;
import web.mvc.dto.place.PlaceDetailImgDTO;
import web.mvc.entity.event.Event;
import web.mvc.enums.event.EventStatus;

import java.text.ParseException;
import java.util.List;

public interface AdminEventService {
    //이벤트 등록
    String createEvent(EventDTO eventDTO, MultipartFile file);

    //이벤트 세부 사진 등록
    String uploadEventDetail(Long eventSeq, MultipartFile file);

    //이벤트 수정
    String updateEvent(Long eventSeq,EventDTO eventDTO,MultipartFile file);

    //이벤트 세부 사진 수정
    String updateEventDetail(Long eventDetailImgSeq, MultipartFile file, EventDetailImgDTO eventDetailImgDTO);

    //이벤트 메인 사진 가져오기
    public Resource getMainImg(String imgName);

    //이벤트 세부 사진 가져오기
    public Resource getDetailImg(String imgName);

    //이벤트 삭제
    void deleteEvent(Long eventSeq);

    //이벤트 세부 사진 삭제
    void deleteEventDetail(Long eventDetailImgSeq);

    //이벤트 전체검색(데드라인 순으로)
    List<EventDTO> selectAllByDeadlLine();


}
