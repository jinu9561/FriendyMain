package web.mvc.entity.event;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDetailImg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "event_detailImg_seq")
    @SequenceGenerator(name ="event_detailImg_seq" , allocationSize = 1 , sequenceName = "event_detailImg_seq")
    private Long eventDetailImgSeq;
    @Column(length = 100)
    private String eventDetailImgName;
    @Column(length = 300)
    private String eventDetailImgSrc;
    @Column(length = 100)
    private String eventDetailImgSize;
    @Column(length = 10)
    private String eventDetailImgType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    public EventDetailImg(Event event) {
        this.event = event;
    }
}
