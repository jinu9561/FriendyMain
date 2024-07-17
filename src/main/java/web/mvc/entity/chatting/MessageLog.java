package web.mvc.entity.chatting;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import web.mvc.entity.user.Users;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MessageLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageLog_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "messageLog_seq", name = "messageLog_seq")
    private Long MessageSeq;

    @JoinColumn(name = "member_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chattingroom_seq")
    private ChattingRoom chattingroom;

    private String chattingContent;

    @CreationTimestamp
    private Date chattingCreateDate;


}
