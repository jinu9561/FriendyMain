package web.mvc.service.generalBoard;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.generalBoard.PhotoBoardDTO;
import web.mvc.dto.generalBoard.PhotoBoardDetailImgDTO;

import java.util.List;
import java.util.Map;

public interface PhotoBoardService {

    // 게시글 전체 조회
    public List<PhotoBoardDTO> getAllPhotoBoardByDefault();

    // 게시글 좋아요순 조회
    public List<PhotoBoardDTO> getAllPhotoBoardByLike();

    // 게시글 최신 등록순 조회
    public List<PhotoBoardDTO> getAllPhotoBoardByRegDate();

    // 게시글 최근 수정순 조회
    public List<PhotoBoardDTO> getAllPhotoBoardByUpdate();

    // 내가 쓴 게시글 조회
    public List<PhotoBoardDTO> getAllPhotoBoardByUser(Long userSeq);

    // 관심사 기반 게시글 조회
    public List<PhotoBoardDTO> getAllPhotoBoardByInterest(String selectInterest);


    // 게시글에 대한 디테일 사진 가져오기
    public List<PhotoBoardDetailImgDTO> getAllPhotoDetail(Long photoBoardSeq);

    // 해당 게시글 조회
    public PhotoBoardDTO getPhotoBoard(Long photoBoardSeq);

    // 게시글 등록
    public Map<String,String>  uploadPhotoBoard(@ModelAttribute PhotoBoardDTO photoBoardDTO, @RequestParam("file") MultipartFile file);

    // 메인 이미지 수정
    public String alterMain(@PathVariable Long photoBoardSeq ,@RequestParam("file") MultipartFile file);

    // 세부 사진 등록
    public String uploadDetail(@PathVariable Long photoBoardSeq ,@RequestParam("file") MultipartFile file);

    // 메인 이미지 가져오기
    public Resource getMainImg(String imgName);

    // 디테일 이미지 가져오기
    public Resource getDetailImg(String imgName);

    // 해당 게시글 수정
    public String alterPhotoBoard(Long photoBoardSeq,PhotoBoardDTO photoBoardDTO);

    // 해당 게시글 삭제
    public String deletePhotoBoard(Long photoBoardSeq);

    // 메인 이미지 삭제
    public String deleteMainlImg(Long photoBoardSeq);

    // 세부 이미지 삭제
    public String deleteDetailImg(Long photoDetailImgSeq);





}
