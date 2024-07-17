package web.mvc.entity.chatting;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import web.mvc.entity.meetUpBoard.MeetUpBoard;
import web.mvc.entity.meetUpBoard.MeetUpRequest;
import web.mvc.entity.user.Users;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChattingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chattingroom_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "chattingroom_seq", name = "chattingroom_seq")
    private Long chattingroomSeq;
    @Column(length = 200)

    @OneToMany(mappedBy = "chattingroom", cascade = CascadeType.ALL  )
    private List<MessageLog> messageLogList;

    private String roomId;
    @CreationTimestamp
    private Date roomRegDate;

    @OneToOne(mappedBy = "chattingroom", cascade = CascadeType.ALL )
    private MeetUpBoard meetUpBoard;

    @OneToMany(mappedBy = "chattingroom", cascade = CascadeType.ALL )
    private List<NoticePost> noticePostList;

    /////////////////////////////진우가 추가한 코드/////////////////////////////
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<ChatParticipant> participants;

    public void addParticipant(Users user) {
        ChatParticipant participant = ChatParticipant.builder()
                .user(user)
                .room(this)
                .build();
        this.participants.add(participant);
    }

}