package web.mvc.entity.event;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import web.mvc.entity.user.Users;
import web.mvc.enums.event.EventStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "event_seq")
    @SequenceGenerator(name ="event_seq" , allocationSize = 1 , sequenceName = "event_seq")
    private Long eventSeq;

    @Column(length = 200)
    private String eventName;

    @Column(length = 500)
    private String eventContent;

    @Column(length = 500)
    private String eventMainImg;

    @Column(length = 100)
    private String eventMainImgName;

    private int eventMaxEntry;

    private EventStatus eventStatus;

    @CreationTimestamp
    private LocalDateTime eventRegDate;

    @UpdateTimestamp
    private LocalDateTime eventUpdateTime;

    @Temporal(TemporalType.DATE)
    private Date eventDeadLine;

    @OneToMany(mappedBy = "event" , cascade = CascadeType.ALL)
    private List<EventDetailImg> eventDetailImg;





}
