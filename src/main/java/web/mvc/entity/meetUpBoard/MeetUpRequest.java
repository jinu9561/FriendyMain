package web.mvc.entity.meetUpBoard;

import jakarta.persistence.*;
import kotlin.Lazy;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import web.mvc.entity.user.Users;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeetUpRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meetuprequest_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "meetuprequest_seq", name = "meetuprequest_seq")
    private Long meetUpRequestSeq;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="meetup_seq")
    private MeetUpBoard meetUpBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_seq")
    private Users user;

    @ColumnDefault("0")
    private int meetUpRequestStatus;
    @CreationTimestamp
    private Date meetUpReqeustRegDate;

    @Column(length = 300)
    private String requestText;
}
