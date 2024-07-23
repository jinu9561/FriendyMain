package web.mvc.repository.generalBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.generalBoard.UserInteraction;

import java.util.List;
import java.util.Optional;

public interface UserInteractionRepository extends JpaRepository<UserInteraction, Long> {

    @Query("SELECT ui FROM UserInteraction ui WHERE ui.user.userSeq = :userSeq AND ui.post.commBoardSeq = :commBoardSeq")
    List<UserInteraction> findByUser_UserSeqAndPost_CommBoardSeq(Long userSeq, Long commBoardSeq);

}
