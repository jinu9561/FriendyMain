package web.mvc.service.generalBoard;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.dto.generalBoard.ReplyDTO;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.generalBoard.Reply;
import web.mvc.entity.user.Users;
import web.mvc.repository.generalBoard.CommunityBoardRepository;
import web.mvc.repository.generalBoard.ReplyRepository;
import web.mvc.repository.user.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReplyServiceImpl implements ReplyService{


    private final ReplyRepository replyRepository;

    private final CommunityBoardRepository communityBoardRepository;

    private final UserRepository usersRepository;

    //해당 번호 게시글의 모든 댓글을 가져오는 메서드
    @Transactional(readOnly = true)
    @Override
    public List<ReplyDTO> getRepliesByCommBoardSeq(Long commBoardSeq) {
        log.info("Fetching all replies by commBoardSeq: {}", commBoardSeq);
        CommunityBoard communityBoard = communityBoardRepository.findById(commBoardSeq).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 없습니다. seq = " + commBoardSeq));
        List<ReplyDTO> fetchedReplies = communityBoard.getReplyList().stream().
                map(Reply::toDTO).
                toList();

        log.info("Fetched {} replies", fetchedReplies.size());
        return fetchedReplies;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReplyDTO> getAllReplies() {
        log.info("Fetching all replies");
        List<ReplyDTO> fetchedReplies = replyRepository.findAll().stream().
                map(Reply::toDTO).
                toList();

        log.info("Fetched {} replies", fetchedReplies.size());
        return fetchedReplies;
    }

    // 새로운 댓글을 생성하는 메서드
    @Transactional
    @Override
    public ReplyDTO addReply(Long commBoardSeq, ReplyDTO replyDTO) {
        log.info("Creating new reply for commBoardSeq: {}", commBoardSeq);

        //해당 게시물이 있는지 조회
        CommunityBoard communityBoard = communityBoardRepository.findById(commBoardSeq).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. seq = " + commBoardSeq));
        //해당 유저가 있는지 조회
        Users user = usersRepository.findById(replyDTO.getUserSeq()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 없습니다. seq = " + replyDTO.getUserSeq()));
        //dto->entity로 변환
        Reply reply = replyDTO.toEntity(user, communityBoard);
        log.info("Converted DTO to entity: {}", reply);
        
        //reply 저장
        Reply savedReply = replyRepository.save(reply);
        log.info("Reply created with SEQ: {}", savedReply.getReplySeq());
        return savedReply.toDTO();
    }



    // 특정 댓글을 삭제하는 메서드
    @Transactional
    @Override
    public String deleteReply(Long replySeq) {
        log.info("Deleting reply with SEQ: {}", replySeq);
        Reply reply = replyRepository.findById(replySeq).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다. seq = " + replySeq));
        replyRepository.delete(reply);
        log.info("Reply deleted with SEQ: {}", replySeq);
        return "reply deleted succefuly.";
    }



}
