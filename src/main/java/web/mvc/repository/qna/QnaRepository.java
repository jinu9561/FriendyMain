package web.mvc.repository.qna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.mvc.entity.qna.Qna;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {

    @Query("select q from Qna q left join fetch q.user where q.user.userSeq = ?1")
    List<Qna> findByQnaByUser(Long userSeq);

//    @Query("select q from Qna q where q.qnaDesc = ?1")
//    public Qna findReplyByQuestion(String question);

    @Query("select q from Qna q where q.qnaDesc like %?1%")
    List<Qna> findRepliesByQuestionContaining(String qnaDesc);

}
