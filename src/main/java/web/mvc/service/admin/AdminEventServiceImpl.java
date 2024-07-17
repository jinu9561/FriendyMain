package web.mvc.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.event.EventDTO;
import web.mvc.dto.event.EventDetailImgDTO;
import web.mvc.entity.event.Event;
import web.mvc.entity.event.EventDetailImg;
import web.mvc.entity.place.PlaceDetailImg;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.event.EventDetailImgRepository;
import web.mvc.repository.event.EventRepository;
import web.mvc.service.common.CommonService;
import web.mvc.service.event.EventService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eventRepository;
    private final EventDetailImgRepository eventDetailImgRepository;
    private final EventService eventService;
    private final CommonService commonService;


    @Value("${event.save.dir}")
    private String uploadDir;
    private String uploadMsg="등록에 성공했습니다.";
    private String alterMsg="수정에 성공했습니다.";

    //등록
    @Override
    public String createEvent(EventDTO eventDTO, MultipartFile file) {
        log.info("createEvent");
        //date형식으로 바꿔기
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        // 잘못된 날짜를 자동으로 유효한 날짜로 변환하는 기능 비활성화 -> 잘못된 날짜는 바로 예외가 나와야 되기 때문에
        formatter.setLenient(false);
        Date deadLine = null;

        Map<String,String> map = commonService.uploadFile(true,file,uploadDir);

        //하이픈 제거
        String date = eventDTO.getEventDeadLine().replaceAll("-","");
        try{
            deadLine = formatter.parse(date);
            Event event = Event.builder()
                    .eventName(eventDTO.getEventName())
                    .eventContent(eventDTO.getEventContent())
                    .eventMainImg(map.get("imgSrc"))
                    .eventMainImgName(map.get("imgName"))
                    .eventDeadLine(deadLine)
                    .eventMaxEntry(eventDTO.getEventMaxEntry())
                    .eventStatus(eventDTO.getEventStatus())
                    .build();

            Event event1 = eventRepository.save(event);
            log.info(event1.toString());
        }catch (ParseException e){
            return "이벤트 등록에 실패했습니다.";
        }
            return "이벤트 등록이 완료되었습니다.";
    }

    //이벤트 세부 사진 등록
    @Override
    public String uploadEventDetail(Long eventSeq, MultipartFile file) {
        Map<String,String> map = commonService.uploadFile(false,file,uploadDir);

        Event event = eventRepository.findById(eventSeq)
                .orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PLACE));

        EventDetailImg eventDetailImg = new EventDetailImg(event);

        eventDetailImg.setEventDetailImgSrc(map.get("imgSrc"));
        eventDetailImg.setEventDetailImgType(map.get("imgType"));
        eventDetailImg.setEventDetailImgSize(map.get("imgSize"));
        eventDetailImg.setEventDetailImgName(map.get("imgName"));

        eventDetailImgRepository.save(eventDetailImg);

        return uploadMsg;
    }

    //수정
    @Override
    public String updateEvent(Long eventSeq,EventDTO eventDTO,MultipartFile file) {

        //date형식으로 바꿔기
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // 잘못된 날짜를 자동으로 유효한 날짜로 변환하는 기능 비활성화 -> 잘못된 날짜는 바로 예외가 나와야 되기 때문에
        formatter.setLenient(false);
        Date deadLine = null;

        Map<String,String> map = commonService.uploadFile(true,file,uploadDir);

        try{
            deadLine = formatter.parse(eventDTO.getEventDeadLine());
            Event event = eventRepository
                    .findById(eventSeq)
                    .orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));
            event.setEventContent(eventDTO.getEventContent());
            event.setEventMainImg(map.get("imgSrc"));
            event.setEventMaxEntry(eventDTO.getEventMaxEntry());
            event.setEventName(eventDTO.getEventName());
            event.setEventDeadLine(deadLine);
            event.setEventStatus(eventDTO.getEventStatus());

        }catch(ParseException e){
            return "수정에 실패했습니다";
        }
        return "수정완료 되었습니다.";
    }

    //이벤트 세부 사진 수정
    @Override
    public String updateEventDetail(Long eventDetailImgSeq, MultipartFile file, EventDetailImgDTO eventDetailImgDTO) {
        EventDetailImg eventDetailImg = eventDetailImgRepository.findById(eventDetailImgSeq).orElseThrow(
                ()->new GlobalException(ErrorCode.NOTFOUND_PLACE)
        );

        if(!file.isEmpty()){
            Map<String,String> map = commonService.uploadFile(false,file,uploadDir);
            eventDetailImg.setEventDetailImgSrc(map.get("imgSrc"));
            eventDetailImg.setEventDetailImgType(map.get("imgType"));
            eventDetailImg.setEventDetailImgSize(map.get("imgSize"));
        }
        return alterMsg;
    }

    //이벤트 메인 사진 가져오기
    @Override
    public Resource getMainImg(String imgName) {
        Resource resource = new FileSystemResource(uploadDir+"\\"+imgName);
        return resource;
    }

    //이벤트 세부 사진 가져오기
    @Override
    public Resource getDetailImg(String imgName) {
        Resource resource = new FileSystemResource(uploadDir+"/detail"+"\\"+imgName);
        return resource;
    }


    //삭제
    @Override
    public void deleteEvent(Long eventSeq) {
        Event event = eventRepository.findById(eventSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PLACE));
        eventRepository.delete(event);
    }

    //이벤트 세부 사진 삭제
    @Override
    public void deleteEventDetail(Long eventDetailImgSeq) {
        eventDetailImgRepository.deleteByEventDetailImgSeq(eventDetailImgSeq);
    }


    //전체검색(데드라인 순으로)
    @Override
    public List<EventDTO> selectAllByDeadlLine() {
        List<EventDTO> eventDTOList = new ArrayList<>();
        List<Event> eventList = eventRepository.findAllByEventDeadLine();

        //date형식으로 바꿔기
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


        for(Event event : eventList){
            String deadLine = formatter.format(event.getEventDeadLine());

            EventDTO dto = EventDTO.builder()
                    .eventSeq(event.getEventSeq())
                    .eventName(event.getEventName())
                    .eventMainImg(event.getEventMainImg())
                    .eventMainImgName(event.getEventMainImgName())
                    .eventDeadLine(deadLine)
                    .eventStatus(event.getEventStatus()) // 관리자 데드라인 검색만 넣음 (EventServiceImpl에는 없음)
                    .build();
            eventDTOList.add(dto);
        }
        return eventDTOList;
    }




}
