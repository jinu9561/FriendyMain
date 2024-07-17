package web.mvc.repository.generalBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.generalBoard.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {   //레포지토리가 관리할 엔티티는 Reply, Reply의 기본키의 타입은 Long

    List<Reply> findByCommunityBoard(CommunityBoard communityBoard);   //게시글에 해당하는 댓글을 가져오는 메서드
}
