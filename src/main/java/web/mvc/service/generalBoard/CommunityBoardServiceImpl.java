package web.mvc.service.generalBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.dto.generalBoard.CommunityBoardDTO;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.generalBoard.UserInteraction;
import web.mvc.entity.user.Users;
import web.mvc.repository.generalBoard.CommunityBoardRepository;
import web.mvc.repository.generalBoard.UserInteractionRepository;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.generalBoard.CommunityBoardService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityBoardServiceImpl implements CommunityBoardService {

    private final CommunityBoardRepository communityBoardRepository;
    private final UserRepository userRepository;
    private final UserInteractionRepository userInteractionRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CommunityBoardDTO> getAllCommunityBoards() {
        log.info("모든 커뮤니티 게시물 조회");
        List<CommunityBoard> communityBoards = communityBoardRepository.findAll();

        if (communityBoards == null || communityBoards.isEmpty()) {
            log.warn("게시물이 없습니다.");
            throw new RuntimeException("게시물이 없습니다.");
        }

        List<CommunityBoardDTO> communityBoardDTOs = communityBoards.stream()
                .map(CommunityBoard::toDTO)
                .collect(Collectors.toList());

        log.info("{}개의 커뮤니티 게시물 조회", communityBoardDTOs.size());
        return communityBoardDTOs;
    }

    @Transactional
    @Override
    public CommunityBoardDTO createCommunityBoard(CommunityBoardDTO communityBoardDTO) {
        log.info("커뮤니티 게시물 생성 - 제목: {}", communityBoardDTO.getBoardTitle());

        Users user = userRepository.findById(communityBoardDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + communityBoardDTO.getUserSeq()));

        CommunityBoard communityBoard = communityBoardDTO.toEntity(user);

        CommunityBoard savedCommunityBoard = communityBoardRepository.save(communityBoard);
        log.info("커뮤니티 게시물 생성 - SEQ: {}", savedCommunityBoard.getCommBoardSeq());

        CommunityBoardDTO savedCommunityBoardDTO = savedCommunityBoard.toDTO();
        log.info("커뮤니티 게시물 DTO 생성 - SEQ: {}", savedCommunityBoardDTO.getCommBoardSeq());
        return savedCommunityBoardDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommunityBoardDTO> getAllRealNameCommunityBoards() {
        log.info("모든 실명 커뮤니티 게시물 조회");
        List<CommunityBoard> communityBoards = communityBoardRepository.findByBoardTypeOrderByBoardRegDateDesc(0);

        if (communityBoards == null || communityBoards.isEmpty()) {
            log.warn("실명 게시물이 없습니다.");
            return null;
        }

        List<CommunityBoardDTO> communityBoardDTOs = communityBoards.stream()
                .map(CommunityBoard::toDTO)
                .collect(Collectors.toList());

        log.info("{}개의 실명 커뮤니티 게시물 조회", communityBoardDTOs.size());
        return communityBoardDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommunityBoardDTO> getAllAnonymousCommunityBoards() {
        log.info("모든 익명 커뮤니티 게시물 조회");
        List<CommunityBoard> communityBoards = communityBoardRepository.findByBoardTypeOrderByBoardRegDateDesc(1);

        if (communityBoards == null || communityBoards.isEmpty()) {
            log.warn("익명 게시물이 없습니다.");
            return null;
        }

        List<CommunityBoardDTO> communityBoardDTOs = communityBoards.stream()
                .map(CommunityBoard::toDTO)
                .collect(Collectors.toList());

        log.info("{}개의 익명 커뮤니티 게시물 조회", communityBoardDTOs.size());
        return communityBoardDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public CommunityBoardDTO getCommunityBoardById(Long commBoardSeq) {
        log.info("커뮤니티 게시물 조회 - SEQ: {}", commBoardSeq);

        CommunityBoard fetchedBoard = communityBoardRepository.findById(commBoardSeq)
                .orElseThrow(() -> new RuntimeException("커뮤니티 게시물을 찾을 수 없습니다: " + commBoardSeq));

        communityBoardRepository.updateCommBoardCount(commBoardSeq);

        CommunityBoardDTO fetchedBoardDTO = fetchedBoard.toDTO();
        log.info("커뮤니티 게시물 조회 - SEQ: {}", fetchedBoardDTO.getCommBoardSeq());

        return fetchedBoardDTO;
    }

    @Transactional
    @Override
    public CommunityBoardDTO updateCommunityBoard(Long commBoardSeq, CommunityBoardDTO communityBoardDTO) {
        log.info("커뮤니티 게시물 수정 - SEQ: {}", commBoardSeq);

        CommunityBoard existingBoard = communityBoardRepository.findById(commBoardSeq)
                .orElseThrow(() -> new RuntimeException("커뮤니티 게시물을 찾을 수 없습니다: " + commBoardSeq));

        Users user = userRepository.findById(communityBoardDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + communityBoardDTO.getUserSeq()));

        existingBoard.setUser(user);
        existingBoard.setBoardTitle(communityBoardDTO.getBoardTitle());
        existingBoard.setBoardContent(communityBoardDTO.getBoardContent());
        existingBoard.setBoardType(communityBoardDTO.getBoardType());
        existingBoard.setBoardLike(communityBoardDTO.getBoardLike());
        existingBoard.setBoardPwd(communityBoardDTO.getBoardPwd());
        existingBoard.setCommBoardCount(communityBoardDTO.getCommBoardCount());

        CommunityBoard updatedCommunityBoard = communityBoardRepository.save(existingBoard);
        log.info("커뮤니티 게시물 수정 - SEQ: {}", updatedCommunityBoard.getCommBoardSeq());

        CommunityBoardDTO updatedCommunityBoardDTO = updatedCommunityBoard.toDTO();

        return updatedCommunityBoardDTO;
    }

    @Transactional
    @Override
    public String deleteCommunityBoard(Long commBoardSeq) {
        log.info("커뮤니티 게시물 삭제 - SEQ: {}", commBoardSeq);

        CommunityBoard deletingBoard = communityBoardRepository.findById(commBoardSeq)
                .orElseThrow(() -> new RuntimeException("커뮤니티 게시물을 찾을 수 없습니다: " + commBoardSeq));

        communityBoardRepository.delete(deletingBoard);
        log.info("커뮤니티 게시물 삭제 - SEQ: {}", commBoardSeq);

        String message = "커뮤니티 게시물이 성공적으로 삭제되었습니다";
        log.info(message);
        return message;
    }

    @Transactional
    @Override
    public List<CommunityBoardDTO> searchCommunityBoards(int boardType, String keyword) {
        List<CommunityBoard> communityBoards = communityBoardRepository.findByBoardTypeAndBoardTitleContainingOrBoardContentContainingOrderByBoardRegDateDesc(
                boardType, keyword, keyword);

        return communityBoards.stream()
                .map(CommunityBoard::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean likeCommunityBoard(Long commBoardSeq, Long userSeq) {
        log.info("likeCommunityBoard called with commBoardSeq: {}, userSeq: {}", commBoardSeq, userSeq);
        List<UserInteraction> existingInteractions = userInteractionRepository.findByUser_UserSeqAndPost_CommBoardSeq(userSeq, commBoardSeq);
        log.info("Existing interactions: {}", existingInteractions);

        if (!existingInteractions.isEmpty() && "LIKE".equals(existingInteractions.get(0).getInteractionType())) {
            log.warn("User has already liked this post.");
            return false; // User has already liked the post
        }

        Users user = userRepository.findById(userSeq).orElseThrow(() -> new RuntimeException("User not found"));
        log.info("User found: {}", user);
        CommunityBoard post = communityBoardRepository.findById(commBoardSeq).orElseThrow(() -> new RuntimeException("Post not found"));
        log.info("Post found: {}", post);

        if (!existingInteractions.isEmpty()) {
            log.info("Deleting existing interaction: {}", existingInteractions.get(0));
            userInteractionRepository.delete(existingInteractions.get(0));
        }

        UserInteraction newInteraction = UserInteraction.builder().user(user).post(post).interactionType("LIKE").build();
        userInteractionRepository.save(newInteraction);
        log.info("New interaction saved: {}", newInteraction);
        communityBoardRepository.incrementLikes(commBoardSeq);
        log.info("Incremented likes for post: {}", commBoardSeq);
        return true;
    }

    @Transactional
    public boolean dislikeCommunityBoard(Long commBoardSeq, Long userSeq) {
        List<UserInteraction> existingInteractions = userInteractionRepository.findByUser_UserSeqAndPost_CommBoardSeq(userSeq, commBoardSeq);
        if (!existingInteractions.isEmpty() && "DISLIKE".equals(existingInteractions.get(0).getInteractionType())) {
            return false; // User has already disliked the post
        }

        Users user = userRepository.findById(userSeq).orElseThrow(() -> new RuntimeException("User not found"));
        CommunityBoard post = communityBoardRepository.findById(commBoardSeq).orElseThrow(() -> new RuntimeException("Post not found"));

        if (!existingInteractions.isEmpty()) {
            userInteractionRepository.delete(existingInteractions.get(0));
        }

        userInteractionRepository.save(UserInteraction.builder().user(user).post(post).interactionType("DISLIKE").build());
        communityBoardRepository.decrementLikes(commBoardSeq);
        return true;
    }
}
