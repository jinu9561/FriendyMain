package web.mvc.entity.generalBoard;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.core.annotation.Order;
import web.mvc.dto.generalBoard.CommunityBoardDTO;
import web.mvc.entity.user.Users;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity//서버 실행시에 해당 객체로 테이블 매핑생성
@Builder
public class CommunityBoard {

    @Id
    //시퀀스 전략을 사용하여 기본 키 값을 자동으로 생성하도록 설정. generator 속성의 값은 아래의 name과 일치해야 함.
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commBoardSeq")
    //sequencName은 데이터베이스 시퀀스의 이름. name은 JPA에서 이 시퀀스를 식별하는 이름.
    @SequenceGenerator(allocationSize = 1, sequenceName = "commBoardSeq", name = "commBoardSeq")
    @Column(name = "COMM_BOARD_SEQ")
    private Long commBoardSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Users user;

    @Column(name = "BOARD_TITLE", nullable = false)
    private String boardTitle;

    @Column(name = "BOARD_CONTENT", nullable = false,  length = 3000) //내용은 길이를 크게 지정
    private String boardContent;

    @Column(name = "BOARD_TYPE", nullable = false)  //0번 : 실명(게시자 정보 표시) 게시판, 1번 : 익명 게시판
    private int boardType;

    @Column(name = "BOARD_LIKE", nullable = false)
    private int boardLike;

    @CreationTimestamp
    @Column(name = "BOARD_REG_DATE", nullable = false)
    private LocalDateTime boardRegDate;

    @UpdateTimestamp
    @Column(name = "BOARD_UPDATE_DATE", nullable = false)
    private LocalDateTime boardUpdateDate;

    @Column(name = "BOARD_PWD", nullable = false)
    private String boardPwd;

    @Column(name = "COMM_BOARD_COUNT", nullable = false)
    private int commBoardCount;

    @OneToMany(mappedBy="communityBoard",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @OrderBy("replySeq asc")//댓글 번호 오름차순 정렬
    private List<Reply> replyList;

    // DTO로 변환
    public CommunityBoardDTO toDTO() {
        return CommunityBoardDTO.builder()
                .commBoardSeq(this.commBoardSeq)
                .userSeq(this.user.getUserSeq())
                .nickName(this.user.getNickName())
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardType(this.boardType)
               .boardLike(this.boardLike)
                .boardPwd(this.boardPwd)
                .boardRegDate(this.boardRegDate)
                .boardUpdateDate(this.boardUpdateDate)
                .commBoardCount(this.commBoardCount)
                .replyList(this.replyList != null ? this.replyList.stream().map(Reply::toDTO).collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }
}