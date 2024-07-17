package web.mvc.dto.generalBoard;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.stereotype.Component;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.user.Users;
import web.mvc.repository.user.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor //모든 멤버를 인자(parameter)로 받아 DTO객체를 생성
@NoArgsConstructor
@Builder
public class CommunityBoardDTO {

    private Long commBoardSeq; //게시글 번호
    private Long userSeq; //게시글 작성자
    private String nickName; //게시글 작성자 닉네임
    private String boardTitle; //게시글 제목
    private String boardContent; //게시글 내용
    private int boardType;  //0이 자유게시판, 1이 익명게시판
    private int boardLike; //추천수
    private String boardPwd;    //게시글 비밀번호
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") //Json으로 보낼 때, LocalDateTime을 String으로 변환
    private LocalDateTime boardRegDate; //게시글 등록일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime boardUpdateDate; //게시글 수정일
    private int commBoardCount; //조회수
    private List<ReplyDTO> replyList; //댓글 리스트
    private int photoBoardCount;
    // 사용자가 보낸 DTO에서 엔티티로 변환하는 메서드
    public CommunityBoard toEntity(Users user) {
        return CommunityBoard.builder()
                .user(user)
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardType(this.boardType)
              .boardLike(this.boardLike)
              .boardPwd(this.boardPwd)
                .build();
    }

}

