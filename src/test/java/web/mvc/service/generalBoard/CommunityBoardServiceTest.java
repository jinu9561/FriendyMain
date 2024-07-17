package web.mvc.service.generalBoard;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.mvc.dto.generalBoard.CommunityBoardDTO;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.user.UserDetail;
import web.mvc.entity.user.Users;
import web.mvc.repository.generalBoard.CommunityBoardRepository;
import web.mvc.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
public class CommunityBoardServiceTest {

    @Autowired
    private CommunityBoardServiceImpl communityBoardService;

    @Autowired
    private CommunityBoardRepository communityBoardRepository;

    @Autowired
    private UserRepository userRepository;

    private Long userSeq;

    @BeforeEach
    public void setUp() {
        UserDetail userDetail = new UserDetail();
        userDetail.setUserJelly(0);

        Users user = new Users();
        user.setUserDetail(userDetail);
        user.setJellyTransactionList(new ArrayList<>());

        userRepository.save(user);
        userSeq = user.getUserSeq();
    }

    @Test
    public void 모든_커뮤니티_게시판_가져오기_테스트() {
        CommunityBoardDTO dto = new CommunityBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setBoardTitle("Test Title");
        dto.setBoardContent("Test Content");
        dto.setBoardType(0); // 실명 게시판
        dto.setBoardLike(0);
        dto.setBoardPwd("1234");
        dto.setCommBoardCount(0);

        communityBoardService.createCommunityBoard(dto);

        List<CommunityBoardDTO> boards = communityBoardService.getAllCommunityBoards();
        assertNotNull(boards);
        assertTrue(boards.size() > 0, "게시물이 존재하지 않습니다.");
    }

    @Test
    public void 커뮤니티_게시판_생성_테스트() {
        CommunityBoardDTO dto = new CommunityBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setBoardTitle("Test Title");
        dto.setBoardContent("Test Content");
        dto.setBoardType(0); // 실명 게시판
        dto.setBoardLike(0);
        dto.setBoardPwd("1234");
        dto.setCommBoardCount(0);

        CommunityBoardDTO createdBoard = communityBoardService.createCommunityBoard(dto);

        assertNotNull(createdBoard);
        assertEquals("Test Title", createdBoard.getBoardTitle());
    }

    @Test
    public void 모든_실명_커뮤니티_게시판_가져오기_테스트() {
        CommunityBoardDTO dto = new CommunityBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setBoardTitle("Real Name Test Title");
        dto.setBoardContent("Real Name Test Content");
        dto.setBoardType(0); // 실명 게시판
        dto.setBoardLike(0);
        dto.setBoardPwd("1234");
        dto.setCommBoardCount(0);

        communityBoardService.createCommunityBoard(dto);

        List<CommunityBoardDTO> realNameBoards = communityBoardService.getAllRealNameCommunityBoards();
        assertNotNull(realNameBoards);
        assertTrue(realNameBoards.size() > 0);
    }

    @Test
    public void 모든_익명_커뮤니티_게시판_가져오기_테스트() {
        CommunityBoardDTO dto = new CommunityBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setBoardTitle("Anonymous Test Title");
        dto.setBoardContent("Anonymous Test Content");
        dto.setBoardType(1); // 익명 게시판
        dto.setBoardLike(0);
        dto.setBoardPwd("1234");
        dto.setCommBoardCount(0);

        communityBoardService.createCommunityBoard(dto);

        List<CommunityBoardDTO> anonymousBoards = communityBoardService.getAllAnonymousCommunityBoards();
        assertNotNull(anonymousBoards);
        assertTrue(anonymousBoards.size() > 0);
    }

    @Test
    public void 커뮤니티_게시판_아이디로_가져오기_테스트() {
        CommunityBoardDTO dto = new CommunityBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setBoardTitle("Test Title");
        dto.setBoardContent("Test Content");
        dto.setBoardType(0); // 실명 게시판
        dto.setBoardLike(0);
        dto.setBoardPwd("1234");
        dto.setCommBoardCount(0);

        CommunityBoardDTO createdBoard = communityBoardService.createCommunityBoard(dto);
        Long boardSeq = createdBoard.getCommBoardSeq();

        CommunityBoardDTO fetchedBoard = communityBoardService.getCommunityBoardById(boardSeq);

        assertNotNull(fetchedBoard);
        assertEquals("Test Title", fetchedBoard.getBoardTitle());
    }

    @Test
    public void 커뮤니티_게시판_수정_테스트() {
        CommunityBoardDTO dto = new CommunityBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setBoardTitle("Original Title");
        dto.setBoardContent("Original Content");
        dto.setBoardType(0); // 실명 게시판
        dto.setBoardLike(0);
        dto.setBoardPwd("1234");
        dto.setCommBoardCount(0);

        CommunityBoardDTO createdBoard = communityBoardService.createCommunityBoard(dto);
        Long boardSeq = createdBoard.getCommBoardSeq();

        dto.setBoardTitle("Updated Title");
        dto.setBoardContent("Updated Content");

        CommunityBoardDTO updatedBoard = communityBoardService.updateCommunityBoard(boardSeq, dto);

        assertNotNull(updatedBoard);
        assertEquals("Updated Title", updatedBoard.getBoardTitle());
        assertEquals("Updated Content", updatedBoard.getBoardContent());
    }

    @Test
    public void 커뮤니티_게시판_삭제_테스트() {
        CommunityBoardDTO dto = new CommunityBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setBoardTitle("Test Title");
        dto.setBoardContent("Test Content");
        dto.setBoardType(0); // 실명 게시판
        dto.setBoardLike(0);
        dto.setBoardPwd("1234");
        dto.setCommBoardCount(0);

        CommunityBoardDTO createdBoard = communityBoardService.createCommunityBoard(dto);
        Long boardSeq = createdBoard.getCommBoardSeq();

        String result = communityBoardService.deleteCommunityBoard(boardSeq);
        assertEquals("CommunityBoard deleted successfully", result);

        assertThrows(RuntimeException.class, () -> {
            communityBoardService.getCommunityBoardById(boardSeq);
        });
    }

    @Test
    public void 커뮤니티_게시판_검색_테스트() {
        CommunityBoardDTO dto = new CommunityBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setBoardTitle("Searchable Title");
        dto.setBoardContent("Searchable Content");
        dto.setBoardType(0); // 실명 게시판
        dto.setBoardLike(0);
        dto.setBoardPwd("1234");
        dto.setCommBoardCount(0);

        communityBoardService.createCommunityBoard(dto);

        List<CommunityBoardDTO> searchResults = communityBoardService.searchCommunityBoards(0, "Searchable");
        assertNotNull(searchResults);
        assertTrue(searchResults.size() > 0);
    }
}
