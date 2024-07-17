package web.mvc.entity.meetUpBoard;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import web.mvc.entity.chatting.ChattingRoom;
import web.mvc.entity.user.Interest;
import web.mvc.entity.user.MeetupRecord;
import web.mvc.entity.user.Users;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetUpBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meetup_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "meetup_seq", name = "meetup_seq")
    private Long meetUpSeq;
    @Column(length = 50)
    private String meetUpName;

    @Column(length = 100)
    private String meetUpDesc;

    @CreationTimestamp
    private Date meetUpRegDate;

    @UpdateTimestamp
    private Date meetUpUpdateTime;

    private int meetUpPwd;

    private int meetUpMaxEntry;

    @ColumnDefault("1")
    private int nowEntry;

    private Date meetUpDeadLine;

    private int meetUpStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_seq")
    private Interest interest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private Users user;

    @OneToMany(mappedBy = "meetUpBoard", cascade = CascadeType.ALL)
    private List<MeetUpBoardDetailImg> meetUpBoardDetailImgList;

    @OneToOne(mappedBy = "meetUpBoard", cascade = CascadeType.ALL)
    private MeetupRecord meetupRecord;

    //아마 문제 없음


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chattingroom_seq")
    private ChattingRoom chattingroom;

    //문제 있음
    @OneToMany(mappedBy = "meetUpBoard", cascade = CascadeType.ALL)
    private List<MeetUpBoardList> meetUpBoardList;

    //문제 있음
    @OneToMany(mappedBy = "meetUpBoard", cascade = CascadeType.ALL)
    private List<MeetUpRequest> meetUpRequestsList;





}
