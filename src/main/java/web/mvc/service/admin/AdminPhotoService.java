package web.mvc.service.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import web.mvc.dto.generalBoard.PhotoBoardDTO;

import java.util.List;

public interface AdminPhotoService {


    // 게시글 전체 조회
    public List<PhotoBoardDTO> getAllPhotoBoardByDefault();

    // 게시글 최신 등록순 조회
    public List<PhotoBoardDTO> getAllPhotoBoardByRegDate();

    // 게시글 최근 수정순 조회
    public List<PhotoBoardDTO> getAllPhotoBoardByUpdate();

    // 해당 게시글 삭제
    public String deletePhotoBoard(Long photoBoardSeq);
}
