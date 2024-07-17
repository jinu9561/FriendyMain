package web.mvc.service.generalBoard;

import org.springframework.web.bind.annotation.PathVariable;
import web.mvc.dto.generalBoard.CommunityBoardDTO;

import java.util.List;

public interface CommunityBoardService {
    CommunityBoardDTO createCommunityBoard(CommunityBoardDTO communityBoardDTO);

    CommunityBoardDTO getCommunityBoardById(Long commBoardSeq);

    List<CommunityBoardDTO> getAllCommunityBoards();

    List<CommunityBoardDTO> getAllRealNameCommunityBoards();

    List<CommunityBoardDTO> getAllAnonymousCommunityBoards();

    CommunityBoardDTO updateCommunityBoard(Long commBoardSeq, CommunityBoardDTO communityBoardDTO);

    String deleteCommunityBoard(Long commBoardSeq);

    public List<CommunityBoardDTO> searchCommunityBoards(int boardType, String keyword);
}
