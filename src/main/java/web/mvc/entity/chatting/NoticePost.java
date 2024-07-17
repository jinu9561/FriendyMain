package web.mvc.entity.chatting;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class NoticePost {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "notice_seq", name ="notice_seq")
    private Long noticeSeq;

    @Column(length = 200, nullable = false)
    private String noticeContent;

    @CreationTimestamp
    private Date noticeCreateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chattingroom_seq")
    private ChattingRoom chattingroom;

    @ColumnDefault("0")
    private int noticePostStatus;




}
