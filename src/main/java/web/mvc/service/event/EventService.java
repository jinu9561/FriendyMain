package web.mvc.service.event;

import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.event.EventDTO;
import web.mvc.dto.event.EventDetailImgDTO;
import java.util.List;

public interface EventService {

    //이벤트 전체검색(데드라인 순으로)
    List<EventDTO> selectAllByDeadlLine();

    //이벤트 전체검색(등록일 순으로)
    List<EventDTO> selectAllByRegDate();

    //이벤트 1개 상세검색(eventSeq로 - 배너에서 사용)
    List<EventDTO> selectEventByEventSeq(Long eventSeq);

    //배너창 세부 이벤트 사진 가져오기
    public List<EventDetailImgDTO> getEventDetailImg(Long eventSeq);
}
