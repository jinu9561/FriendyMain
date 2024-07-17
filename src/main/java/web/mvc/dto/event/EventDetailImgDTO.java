package web.mvc.dto.event;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EventDetailImgDTO {

    private Long eventDetailImgSeq;

    private String eventDetailImgName;

    private String eventDetailImgSrc;

    private String eventDetailImgSize;

    private String eventDetailImgType;
}
