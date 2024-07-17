package web.mvc.service.generalBoard;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.mvc.dto.generalBoard.PhotoBoardDTO;
import web.mvc.entity.generalBoard.PhotoBoard;
import web.mvc.entity.generalBoard.PhotoBoardDetailImg;
import web.mvc.entity.user.UserDetail;
import web.mvc.entity.user.Users;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.generalBoard.PhotoBoardDetailImgRepository;
import web.mvc.repository.generalBoard.PhotoBoardRepository;
import web.mvc.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class PhotoBoardServiceTest {

    @Autowired
    private PhotoBoardServiceImpl photoBoardService;

    @Autowired
    private PhotoBoardRepository photoBoardRepository;

    @Autowired
    private PhotoBoardDetailImgRepository photoBoardDetailImgRepository;

    @Autowired
    private UserRepository userRepository;

    private Long userSeq;

    @BeforeEach
    public void setUp() {
        UserDetail userDetail = new UserDetail();
        userDetail.setUserJelly(0);

        Users user = Users.builder()
                .userDetail(userDetail)
                .userId("testUser")
                .userPwd("testPwd")
                .userName("Test User")
                .nickName("Tester")
                .birth(new Date())
                .address("123 Test St")
                .email("test@example.com")
                .phone("123-456-7890")
                .Role("USER")
                .jellyTransactionList(new ArrayList<>())
                .build();

        userDetail.setUser(user);

        userRepository.save(user);
        userSeq = user.getUserSeq();
    }

    @Test
    public void 모든_포토보드_기본_가져오기_테스트() {
        List<PhotoBoardDTO> boards = photoBoardService.getAllPhotoBoardByDefault();
        log.info("모든_포토보드_기본_가져오기_테스트 - 게시물 수: {}", boards.size());
    }

    @Test
    public void 모든_포토보드_좋아요_순_가져오기_테스트() {
        List<PhotoBoardDTO> boards = photoBoardService.getAllPhotoBoardByLike();
        log.info("모든_포토보드_좋아요_순_가져오기_테스트 - 게시물 수: {}", boards.size());
    }

    @Test
    public void 모든_포토보드_등록_날짜_순_가져오기_테스트() {
        List<PhotoBoardDTO> boards = photoBoardService.getAllPhotoBoardByRegDate();
        log.info("모든_포토보드_등록_날짜_순_가져오기_테스트 - 게시물 수: {}", boards.size());
    }

    @Test
    public void 모든_포토보드_업데이트_순_가져오기_테스트() {
        List<PhotoBoardDTO> boards = photoBoardService.getAllPhotoBoardByUpdate();
        log.info("모든_포토보드_업데이트_순_가져오기_테스트 - 게시물 수: {}", boards.size());
    }

    @Test
    public void 특정_유저의_모든_포토보드_가져오기_테스트() {
        List<PhotoBoardDTO> boards = photoBoardService.getAllPhotoBoardByUser(userSeq);
        log.info("특정_유저의_모든_포토보드_가져오기_테스트 - 게시물 수: {}", boards.size());
    }

    @Test
    public void 관심사_기반_모든_포토보드_가져오기_테스트() {
        List<PhotoBoardDTO> boards = photoBoardService.getAllPhotoBoardByInterest("testInterest");
        log.info("관심사_기반_모든_포토보드_가져오기_테스트 - 게시물 수: {}", boards.size());
    }

    @Test
    public void 포토보드_가져오기_테스트() {
        PhotoBoardDTO dto = new PhotoBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setPhotoBoardTitle("Test Title");
        dto.setPhotoBoardLike(0);
        dto.setInterestCategory(new ArrayList<>());

        // 데이터 생성 및 저장
        PhotoBoard photoBoard = PhotoBoard.builder()
                .user(userRepository.findById(userSeq).orElse(null))
                .photoBoardTitle(dto.getPhotoBoardTitle())
                .build();
        photoBoardRepository.save(photoBoard);
        Long boardSeq = photoBoard.getPhotoBoardSeq();

        PhotoBoardDTO fetchedBoard = photoBoardService.getPhotoBoard(boardSeq);

        log.info("포토보드_가져오기_테스트 - 게시물 제목: {}", fetchedBoard.getPhotoBoardTitle());
    }

    @Test
    public void 포토보드_업로드_테스트() {
        PhotoBoardDTO dto = new PhotoBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setPhotoBoardTitle("Test Title");
        dto.setPhotoBoardLike(0);
        dto.setInterestCategory(new ArrayList<>());

        // 파일 업로드 없이 데이터 저장 로직으로 대체
        PhotoBoard photoBoard = PhotoBoard.builder()
                .user(userRepository.findById(userSeq).orElse(null))
                .photoBoardTitle(dto.getPhotoBoardTitle())
                .build();
        photoBoardRepository.save(photoBoard);

        log.info("포토보드_업로드_테스트 - photoBoardSeq: {}", photoBoard.getPhotoBoardSeq());
    }

    @Test
    public void 포토보드_수정_테스트() {
        PhotoBoardDTO dto = new PhotoBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setPhotoBoardTitle("Test Title");
        dto.setPhotoBoardLike(0);
        dto.setInterestCategory(new ArrayList<>());

        // 데이터 생성 및 저장
        PhotoBoard photoBoard = PhotoBoard.builder()
                .user(userRepository.findById(userSeq).orElse(null))
                .photoBoardTitle(dto.getPhotoBoardTitle())
                .build();
        photoBoardRepository.save(photoBoard);
        Long boardSeq = photoBoard.getPhotoBoardSeq();

        // 파일 업로드 없이 데이터 수정 로직으로 대체
        photoBoard.setPhotoBoardTitle("Updated Title");
        photoBoardRepository.save(photoBoard);

        log.info("포토보드_수정_테스트 - updatedTitle: {}", photoBoard.getPhotoBoardTitle());
    }

    @Test
    public void 포토보드_상세이미지_업로드_테스트() {
        PhotoBoardDTO dto = new PhotoBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setPhotoBoardTitle("Test Title");
        dto.setPhotoBoardLike(0);
        dto.setInterestCategory(new ArrayList<>());

        // 데이터 생성 및 저장
        PhotoBoard photoBoard = PhotoBoard.builder()
                .user(userRepository.findById(userSeq).orElse(null))
                .photoBoardTitle(dto.getPhotoBoardTitle())
                .build();
        photoBoardRepository.save(photoBoard);
        Long boardSeq = photoBoard.getPhotoBoardSeq();

        // 파일 업로드 없이 데이터 저장 로직으로 대체
        PhotoBoardDetailImg detailImg = PhotoBoardDetailImg.builder()
                .photoBoard(photoBoard)
                .photoDetailImgName("Detail Image")
                .photoDetailImgSrc("path/to/image")
                .build();
        photoBoardDetailImgRepository.save(detailImg);

        log.info("포토보드_상세이미지_업로드_테스트 - detailImgSeq: {}", detailImg.getPhotoDetailImgSeq());
    }

    @Test
    public void 포토보드_삭제_테스트() {
        PhotoBoardDTO dto = new PhotoBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setPhotoBoardTitle("Test Title");
        dto.setPhotoBoardLike(0);
        dto.setInterestCategory(new ArrayList<>());

        // 데이터 생성 및 저장
        PhotoBoard photoBoard = PhotoBoard.builder()
                .user(userRepository.findById(userSeq).orElse(null))
                .photoBoardTitle(dto.getPhotoBoardTitle())
                .build();
        photoBoardRepository.save(photoBoard);
        Long boardSeq = photoBoard.getPhotoBoardSeq();

        String deleteResult = photoBoardService.deletePhotoBoard(boardSeq);
        log.info("포토보드_삭제_테스트 - 결과 메시지: {}", deleteResult);

        try {
            photoBoardService.getPhotoBoard(boardSeq);
        } catch (GlobalException e) {
            log.info("포토보드_삭제_테스트 - 예외 메시지: {}", e.getMessage());
        }
    }

    @Test
    public void 포토보드_메인이미지_삭제_테스트() {
        PhotoBoardDTO dto = new PhotoBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setPhotoBoardTitle("Test Title");
        dto.setPhotoBoardLike(0);
        dto.setInterestCategory(new ArrayList<>());

        // 데이터 생성 및 저장
        PhotoBoard photoBoard = PhotoBoard.builder()
                .user(userRepository.findById(userSeq).orElse(null))
                .photoBoardTitle(dto.getPhotoBoardTitle())
                .build();
        photoBoardRepository.save(photoBoard);
        Long boardSeq = photoBoard.getPhotoBoardSeq();

        String deleteImgResult = photoBoardService.deleteMainlImg(boardSeq);

        log.info("포토보드_메인이미지_삭제_테스트 - 결과 메시지: {}", deleteImgResult);
    }

    @Test
    public void 포토보드_상세이미지_삭제_테스트() {
        PhotoBoardDTO dto = new PhotoBoardDTO();
        dto.setUserSeq(userSeq);
        dto.setPhotoBoardTitle("Test Title");
        dto.setPhotoBoardLike(0);
        dto.setInterestCategory(new ArrayList<>());

        // 데이터 생성 및 저장
        PhotoBoard photoBoard = PhotoBoard.builder()
                .user(userRepository.findById(userSeq).orElse(null))
                .photoBoardTitle(dto.getPhotoBoardTitle())
                .build();
        photoBoardRepository.save(photoBoard);
        Long boardSeq = photoBoard.getPhotoBoardSeq();

        // Detail image 생성 및 저장
        PhotoBoardDetailImg detailImg = PhotoBoardDetailImg.builder()
                .photoBoard(photoBoard)
                .photoDetailImgName("Detail Image")
                .photoDetailImgSrc("path/to/image")
                .build();
        photoBoardDetailImgRepository.save(detailImg);

        List<PhotoBoardDetailImg> detailImgs = photoBoardDetailImgRepository.findPhotoDetailImgByPhotoBoardSeq(boardSeq);
        log.info("포토보드_상세이미지_삭제_테스트 - 상세 이미지 수: {}", detailImgs.size());

        Long detailImgSeq = detailImgs.get(0).getPhotoDetailImgSeq();
        String deleteDetailImgResult = photoBoardService.deleteDetailImg(detailImgSeq);

        log.info("포토보드_상세이미지_삭제_테스트 - 결과 메시지: {}", deleteDetailImgResult);
    }
}
