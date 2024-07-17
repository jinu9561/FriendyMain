package web.mvc.service.generalBoard;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.mvc.dto.generalBoard.ReplyDTO;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.user.UserDetail;
import web.mvc.entity.user.Users;
import web.mvc.repository.generalBoard.CommunityBoardRepository;
import web.mvc.repository.generalBoard.ReplyRepository;
import web.mvc.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class ReplyServiceTest {

    @Autowired
    private ReplyServiceImpl replyService;

    @Autowired
    private CommunityBoardRepository communityBoardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    private Long userSeq;
    private Long commBoardSeq;

    @BeforeEach
    public void setUp() {
        UserDetail userDetail = new UserDetail();
        userDetail.setUserJelly(0);

        Users user = Users.builder()
                .userDetail(userDetail)
                .userId("testUser")
                .userPwd("testPwd")
                .userName("Test User")
                .nickName("Tester")
                .birth(new Date())
                .address("123 Test St")
                .email("test@example.com")
                .phone("123-456-7890")
                .Role("USER")
                .jellyTransactionList(new ArrayList<>())
                .build();

        userDetail.setUser(user);

        userRepository.save(user);
        userSeq = user.getUserSeq();

        CommunityBoard communityBoard = CommunityBoard.builder()
                .user(user)
                .boardTitle("Test Title")
                .boardContent("Test Content")
                .boardType(0) // 실명 게시판 또는 익명 게시판 타입 설정
                .boardLike(0)
                .boardPwd("1234")
                .commBoardCount(0)
                .replyList(new ArrayList<>())
                .build();

        communityBoardRepository.save(communityBoard);
        commBoardSeq = communityBoard.getCommBoardSeq();
    }

    @Test
    public void 게시물_댓글_가져오기_테스트() {
        List<ReplyDTO> replies = replyService.getRepliesByCommBoardSeq(commBoardSeq);
        log.info("게시물_댓글_가져오기_테스트 - 댓글 수: {}", replies.size());
    }

    @Test
    public void 모든_댓글_가져오기_테스트() {
        List<ReplyDTO> replies = replyService.getAllReplies();
        log.info("모든_댓글_가져오기_테스트 - 댓글 수: {}", replies.size());
    }

    @Test
    public void 댓글_추가_테스트() {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setUserSeq(userSeq);
        replyDTO.setReplyContent("Test Reply");

        ReplyDTO createdReply = replyService.addReply(commBoardSeq, replyDTO);

        log.info("댓글_추가_테스트 - 댓글 내용: {}", createdReply.getReplyContent());
    }

    @Test
    public void 댓글_삭제_테스트() {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setUserSeq(userSeq);
        replyDTO.setReplyContent("Test Reply");

        ReplyDTO createdReply = replyService.addReply(commBoardSeq, replyDTO);
        Long replySeq = createdReply.getReplySeq();

        String result = replyService.deleteReply(replySeq);
        log.info("댓글_삭제_테스트 - 결과 메시지: {}", result);

        try {
            replyService.deleteReply(replySeq);
        } catch (IllegalArgumentException e) {
            log.info("댓글_삭제_테스트 - 예외 메시지: {}", e.getMessage());
        }
    }
}
