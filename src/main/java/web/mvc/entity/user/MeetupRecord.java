package web.mvc.entity.user;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import web.mvc.entity.meetUpBoard.MeetUpBoard;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetupRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "meetup_record_seq")
    @SequenceGenerator(name ="meetup_record_seq" , allocationSize = 1 , sequenceName = "meetup_record_seq")
    private Long meetUpRecordSeq;
    @CreationTimestamp
    private LocalDateTime userRegDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meetup_seq")
    private MeetUpBoard meetUpBoard;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")  // 외래 키를 지정
    private UserDetail userDetail;
}
