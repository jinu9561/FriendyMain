package web.mvc.service.qna;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web.mvc.dto.qna.QnaDTO;
import web.mvc.entity.qna.Qna;
import web.mvc.repository.qna.QnaRepository;
import web.mvc.repository.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QnaServiceImpl implements QnaService{

    private final QnaRepository qnaRepository;
    private final UserRepository userRepository;

    public List<QnaDTO> getQnaList(Long userSeq) {
        List<Qna> list = qnaRepository.findByQnaByUser(userSeq);

        return list.stream().map(QnaDTO::new).collect(Collectors.toList());
    }

    public String getQnaReply(String qnaDesc) {
        List<Qna> qnaList = qnaRepository.findRepliesByQuestionContaining(qnaDesc);
        if (!qnaList.isEmpty()) {
            return qnaList.get(0).getQnaReply();
        } else {
            return "죄송해요 무슨 말씀인지 알아듣지 못했어요.";
        }
    }

    @Override
    public Qna insertQna(Qna qna) {
        Qna savedQna = qnaRepository.save(qna);
        log.info("savedQna = " + savedQna);
        return savedQna;
    }

}
