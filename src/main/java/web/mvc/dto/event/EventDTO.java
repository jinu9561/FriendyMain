package web.mvc.dto.event;


import lombok.*;
import web.mvc.enums.event.EventStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EventDTO {
    private Long eventSeq;
    private String eventName;
    private String eventContent;
    private String eventMainImg;
    private String eventMainImgName;
    private String eventRegDate;
    private String eventUpdateTime;
    private String eventDeadLine;
    private int eventMaxEntry;
    private EventStatus eventStatus;
    private List<EventDetailImgDTO> eventDetailImgList;
    private List<EventDetailImgDTO> eventDetailImgDTOList;


}
