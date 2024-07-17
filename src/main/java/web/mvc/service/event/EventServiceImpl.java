package web.mvc.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.event.EventDTO;
import web.mvc.dto.event.EventDetailImgDTO;
import web.mvc.entity.event.Event;
import web.mvc.entity.event.EventDetailImg;
import web.mvc.repository.event.EventDetailImgRepository;
import web.mvc.repository.event.EventRepository;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final EventDetailImgRepository eventDetailImgRepository;


    //전체검색(데드라인 순으로)
    @Override
    public List<EventDTO> selectAllByDeadlLine() {
        List<EventDTO> eventDTOList = new ArrayList<>();
        List<Event> eventList = eventRepository.findAllByEventDeadLine();

        //date형식으로 바꿔주기
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        for(Event event : eventList){
            String deadLine = formatter.format(event.getEventDeadLine());

            EventDTO dto = EventDTO.builder()
                    .eventSeq(event.getEventSeq())
                    .eventName(event.getEventName())
                    .eventMainImg(event.getEventMainImg())
                    .eventMainImgName(event.getEventMainImgName())
                    .eventDeadLine(deadLine)
                    .build();
            eventDTOList.add(dto);
        }
        return eventDTOList;
    }

    //전체검색(등록일 순으로)
    @Override
    public List<EventDTO> selectAllByRegDate() {

        List<EventDTO> eventDTOList = new ArrayList<>();
        List<Event> eventList = eventRepository.findAllByEventRegDate();

        //date형식으로 바꿔주기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for(Event event : eventList){
            // LocalDateTime을 String으로 변환
            String eventRegDate = event.getEventRegDate().format(formatter);

            EventDTO dto = EventDTO.builder()
                    .eventSeq(event.getEventSeq())
                    .eventName(event.getEventName())
                    .eventMainImg(event.getEventMainImg())
                    .eventMainImgName(event.getEventMainImgName())
                    .eventRegDate(eventRegDate)
                    .build();
            eventDTOList.add(dto);
        }
        return eventDTOList;
    }

    //이벤트 1개 상세검색(eventSeq로 - 배너에서 사용)
    @Override
    public List<EventDTO> selectEventByEventSeq(Long eventSeq) {

        List<EventDTO> eventDTOList = new ArrayList<>();
        List<Event> eventList = eventRepository.findEventByEventSeq(eventSeq);

        //date형식으로 바꿔주기
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Event event : eventList) {
            String eventRegDate = event.getEventRegDate().format(formatter1);
            String deadLine = formatter2.format(event.getEventDeadLine());
            String eventUpDate = event.getEventRegDate().format(formatter3);

            List<EventDetailImgDTO> eventDetailImgDTOList = this.getEventDetailImg(event.getEventSeq());

            EventDTO dto = EventDTO.builder()
                    .eventSeq(event.getEventSeq())
                    .eventName(event.getEventName())
                    .eventContent(event.getEventContent())
                    .eventMainImg(event.getEventMainImg())
                    .eventMainImgName(event.getEventMainImgName())
                    .eventRegDate(eventRegDate)
                    .eventUpdateTime(eventUpDate)
                    .eventDeadLine(deadLine)
                    .eventMaxEntry(event.getEventMaxEntry())
                    .eventDetailImgDTOList(eventDetailImgDTOList)
                    .build();
            eventDTOList.add(dto);
        }

        return eventDTOList;
    }



    //배너창 세부 이벤트 사진 가져오기
    @Override
    public List<EventDetailImgDTO> getEventDetailImg(Long eventSeq) {
        List<EventDetailImgDTO> eventDetailImgDTOList = new ArrayList<>();
        List<EventDetailImg> eventDetailImgList = eventDetailImgRepository.findByEventSeq(eventSeq);

        for (EventDetailImg eventDetailImg : eventDetailImgList) {
            EventDetailImgDTO dto = EventDetailImgDTO.builder()
                    .eventDetailImgSeq(eventDetailImg.getEventDetailImgSeq())
                    .eventDetailImgSrc(eventDetailImg.getEventDetailImgSrc())
                    .eventDetailImgSize(eventDetailImg.getEventDetailImgSize())
                    .eventDetailImgType(eventDetailImg.getEventDetailImgType())
                    .eventDetailImgName(eventDetailImg.getEventDetailImgName())
                    .build();
            eventDetailImgDTOList.add(dto);
        }
        return eventDetailImgDTOList;
    }

}
