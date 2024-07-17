package web.mvc.service.qna;

import org.springframework.stereotype.Service;
import web.mvc.dto.qna.QnaDTO;
import web.mvc.entity.qna.Qna;

import java.util.List;

@Service
public interface QnaService {

    /**
     * 해당 유저의 qna 질문 목록 전체 출력
     * */
    public List<QnaDTO> getQnaList(Long userSeq);

    /**
     * 유저가 입력한 키워드 포함한 답변 출력
     * */
    public String getQnaReply(String qnaDesc);

    /**
     *
     * */
    Qna insertQna(Qna qna);
}
