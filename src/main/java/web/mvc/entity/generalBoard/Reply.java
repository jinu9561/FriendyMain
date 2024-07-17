package web.mvc.entity.generalBoard;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import web.mvc.dto.generalBoard.ReplyDTO;
import web.mvc.entity.user.Users;
import web.mvc.repository.user.UserRepository;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity//서버 실행시에 해당 객체로 테이블 매핑생성
@Builder
public class Reply {
    @Id
    //시퀀스 전략을 사용하여 기본 키 값을 자동으로 생성하도록 설정. generator 속성의 값은 아래의 name과 일치해야 함.
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "replySeq")
    //sequencName은 데이터베이스 시퀀스의 이름. name은 JPA에서 이 시퀀스를 식별하는 이름.
    @SequenceGenerator(allocationSize = 1, sequenceName = "replySeq", name = "replySeq")
    @Column(name = "REPLY_SEQ")
    private Long replySeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private CommunityBoard communityBoard;

    @Column(name = "REPLY_CONTENT", nullable = false)
    private String replyContent;

    @CreationTimestamp
    @Column(name = "REPLY_REG_DATE", nullable = false)
    private LocalDateTime replyRegDate;


    /*엔티티에서 DTO로 변환*/
    public ReplyDTO toDTO(){
        return ReplyDTO.builder()
                .replySeq(this.replySeq)
                .userSeq(this.user.getUserSeq())
                .commBoardSeq(this.communityBoard.getCommBoardSeq())
                .replyContent(this.replyContent)
                .replyRegDate(this.replyRegDate)
                .nickName(this.user.getNickName())
                .build();

    }
}
