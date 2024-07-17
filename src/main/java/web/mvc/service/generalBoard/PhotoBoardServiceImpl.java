package web.mvc.service.generalBoard;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.generalBoard.PhotoBoardDTO;
import web.mvc.dto.generalBoard.PhotoBoardDetailImgDTO;
import web.mvc.dto.user.InterestDTO;
import web.mvc.entity.generalBoard.PhotoBoard;
import web.mvc.entity.generalBoard.PhotoBoardDetailImg;
import web.mvc.entity.generalBoard.PhotoBoardInterest;
import web.mvc.entity.user.Interest;
import web.mvc.entity.user.Users;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.generalBoard.PhotoBoardDetailImgRepository;
import web.mvc.repository.generalBoard.PhotoBoardInterestRepository;
import web.mvc.repository.generalBoard.PhotoBoardRepository;
import web.mvc.repository.user.InterestRepository;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.common.CommonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PhotoBoardServiceImpl implements PhotoBoardService {

    private final PhotoBoardRepository photoBoardRepository;
    private final PhotoBoardDetailImgRepository photoBoardDetailImgRepository;
    private final CommonService commonService;
    private final PhotoBoardInterestRepository photoBoardInterestRepository;
    private final InterestRepository interestRepository;
    private final UserRepository userRepository;

    @Value("${photo.save.dir}")
    private String uploadDir;
    private String uploadMsg="등록에 성공했습니다.";
    private String alterMsg="수정에 성공했습니다.";
    private String deleteMsg="삭제에 성공했습니다";
    private String failMsg="수정에 실패했습니다.";

    @Override
    public List<PhotoBoardDTO> getAllPhotoBoardByDefault() {
        List<PhotoBoard> photoBoards = photoBoardRepository.findAll();
        return this.getAllPhotoBoard(photoBoards);
    }

    @Override
    public List<PhotoBoardDTO> getAllPhotoBoardByLike() {
        List<PhotoBoard> photoBoards = photoBoardRepository.findAllByOrderByPhotoBoardLikeDesc();
        return this.getAllPhotoBoard(photoBoards);
    }

    @Override
    public List<PhotoBoardDTO> getAllPhotoBoardByRegDate() {
        List<PhotoBoard> photoBoards = photoBoardRepository.findAllByOrderByPhotoBoardRegDateDesc();
        return this.getAllPhotoBoard(photoBoards);
    }

    @Override
    public List<PhotoBoardDTO> getAllPhotoBoardByUpdate() {
        List<PhotoBoard> photoBoards = photoBoardRepository.findAllByOrderByPhotoBoardUpdateDateDesc();
        return this.getAllPhotoBoard(photoBoards);
    }

    @Override
    public List<PhotoBoardDTO> getAllPhotoBoardByUser(Long userSeq) {
        List<PhotoBoard> photoBoards = photoBoardRepository.findAllByUserSeqOrderByPhotoBoardRegDateDesc(userSeq);
        return this.getAllPhotoBoard(photoBoards);
    }

    @Override
    public List<PhotoBoardDTO> getAllPhotoBoardByInterest(String selectInterest) {
        List<PhotoBoard> photoBoards = photoBoardRepository.findAllByInterestOrderByPhotoBoardRegDateDesc(selectInterest);
        return this.getAllPhotoBoard(photoBoards);
    }

    public List<PhotoBoardDTO> getAllPhotoBoard(List<PhotoBoard> photoBoards) {

        List<PhotoBoardDTO> photoBoardDTOs = new ArrayList<>();

        for (PhotoBoard photoBoard : photoBoards) {
            List<PhotoBoardDetailImgDTO> list = this.getAllPhotoDetail(photoBoard.getPhotoBoardSeq());
            List<PhotoBoardInterest> photoBoardInterestList = photoBoardInterestRepository.findByPhotoBoardSeq(photoBoard.getPhotoBoardSeq());
            List<InterestDTO> interestDTOList = this.getInterestDTOList(photoBoardInterestList);

            PhotoBoardDTO photoBoardDTO = PhotoBoardDTO.builder()
                    .photoBoardSeq(photoBoard.getPhotoBoardSeq())
                    .photoBoardTitle(photoBoard.getPhotoBoardTitle())
                    .photoMainImgName(photoBoard.getPhotoMainImgName())
                    .photoMainImgSrc(photoBoard.getPhotoMainImgSrc())
                    .photoMainImgType(photoBoard.getPhotoMainImgType())
                    .photoMainImgSize(photoBoard.getPhotoMainImgSize())
                    .photoBoardLike(photoBoard.getPhotoBoardLike())
                    .photoBoardDetailImgList(list)
                    .interestDTOList(interestDTOList)
                    .photoBoardRegDate(photoBoard.getPhotoBoardRegDate())
                    .photoBoardUpdateDate(photoBoard.getPhotoBoardUpdateDate())
                    .userSeq(photoBoard.getUser().getUserSeq())
                    .build();
            photoBoardDTOs.add(photoBoardDTO);
        }

        return photoBoardDTOs;
    }

    //취미 DTO로 변환
    public List<InterestDTO> getInterestDTOList(List<PhotoBoardInterest> photoBoardInterestList) {
        List<InterestDTO> interestDTOList = new ArrayList<>();

        for(PhotoBoardInterest photoBoardInterest : photoBoardInterestList) {
            InterestDTO interestDTO = InterestDTO.builder()
                    .interestSeq(photoBoardInterest.getInterest().getInterestSeq())
                    .interestCategory(photoBoardInterest.getInterest().getInterestCategory())
                    .build();
            interestDTOList.add(interestDTO);
        }
        return interestDTOList;
    }

    @Override
    public List<PhotoBoardDetailImgDTO> getAllPhotoDetail(Long photoBoardSeq) {
        List<PhotoBoardDetailImg> photoBoardDetailImgList = photoBoardDetailImgRepository.findPhotoDetailImgByPhotoBoardSeq(photoBoardSeq);
        List<PhotoBoardDetailImgDTO> photoBoardDetailImgDTOs = new ArrayList<>();

        for (PhotoBoardDetailImg photoBoardDetailImg : photoBoardDetailImgList) {
            PhotoBoardDetailImgDTO photoBoardDetailImgDTO = PhotoBoardDetailImgDTO.builder()
                    .photoDetailImgSeq(photoBoardDetailImg.getPhotoDetailImgSeq())
                    .photoDetailImgName(photoBoardDetailImg.getPhotoDetailImgName())
                    .photoDetailImgSrc(photoBoardDetailImg.getPhotoDetailImgSrc())
                    .photoDetailImgType(photoBoardDetailImg.getPhotoDetailImgType())
                    .photoDetailImgSize(photoBoardDetailImg.getPhotoDetailImgSize())
                    .build();

            photoBoardDetailImgDTOs.add(photoBoardDetailImgDTO);

        }

        return photoBoardDetailImgDTOs;
    }

    @Override
    public PhotoBoardDTO getPhotoBoard(Long photoBoardSeq) {
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq).orElseThrow(()-> new GlobalException(ErrorCode.NOTFOUND_PHOTOBOARD));
        List<PhotoBoardInterest> photoBoardInterestList = photoBoardInterestRepository.findByPhotoBoardSeq(photoBoardSeq);
        List<InterestDTO> interestDTOList = this.getInterestDTOList(photoBoardInterestList);

        List<PhotoBoardDetailImgDTO> list = this.getAllPhotoDetail(photoBoardSeq);

        PhotoBoardDTO photoBoardDTO = PhotoBoardDTO.builder()
                .photoBoardSeq(photoBoard.getPhotoBoardSeq())
                .photoBoardTitle(photoBoard.getPhotoBoardTitle())
                .photoMainImgName(photoBoard.getPhotoMainImgName())
                .photoMainImgSrc(photoBoard.getPhotoMainImgSrc())
                .photoMainImgType(photoBoard.getPhotoMainImgType())
                .photoMainImgSize(photoBoard.getPhotoMainImgSize())
                .photoBoardLike(photoBoard.getPhotoBoardLike())
                .photoBoardDetailImgList(list)
                .userSeq(photoBoard.getUser().getUserSeq())
                .interestDTOList(interestDTOList)
                .build();

        return photoBoardDTO;
    }

    @Override
    public Map<String,String>  uploadPhotoBoard(PhotoBoardDTO photoBoardDTO, MultipartFile file) {
        Map<String,String> map = commonService.uploadFile(true,file,uploadDir);
        List<String> interests = photoBoardDTO.getInterestCategory();
        Users user = userRepository.findById(photoBoardDTO.getUserSeq()).orElseThrow(()-> new GlobalException(ErrorCode.NOTFOUND_ID));

        PhotoBoard photoBoard = PhotoBoard.builder()
                .photoBoardTitle(photoBoardDTO.getPhotoBoardTitle())
                .photoMainImgName(map.get("imgName"))
                .photoMainImgType(map.get("imgType"))
                .photoMainImgSize(map.get("imgSize"))
                .photoMainImgSrc(map.get("imgSrc"))
                .photoBoardLike(0)
                .user(user)
                .build();

        for(String i : interests){
            Interest interest = interestRepository.findByInterestCategory(i);
            PhotoBoardInterest saveInterest = new PhotoBoardInterest(interest,photoBoard);
            if (photoBoard.getPhotoBoardInterestList() == null) {
                photoBoard.setPhotoBoardInterestList(new ArrayList<>());
            }
            photoBoard.getPhotoBoardInterestList().add(saveInterest);
        }

        photoBoardRepository.save(photoBoard);
        map.put("photoBoardSeq",photoBoard.getPhotoBoardSeq().toString());
        map.put("msg",uploadMsg);

        return map;
    }

    @Override
    public String alterMain(Long photoBoardSeq, MultipartFile file) {
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq).orElseThrow(()-> new GlobalException(ErrorCode.NOTFOUND_PHOTOBOARD));
        Map<String,String> map = commonService.uploadFile(true,file,uploadDir);

        photoBoard.setPhotoMainImgName(map.get("imgName"));
        photoBoard.setPhotoMainImgType(map.get("imgType"));
        photoBoard.setPhotoMainImgSize(map.get("imgSize"));
        photoBoard.setPhotoMainImgSrc(map.get("imgSrc"));

        return alterMsg;
    }

    @Override
    public String uploadDetail(Long photoBoardSeq, MultipartFile file) {
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq).orElseThrow(()-> new GlobalException(ErrorCode.NOTFOUND_PHOTOBOARD));

        Map<String,String> map = commonService.uploadFile(false,file,uploadDir);

        PhotoBoardDetailImg photoBoardDetailImg = PhotoBoardDetailImg.builder()
                .photoDetailImgName(map.get("imgName"))
                .photoDetailImgType(map.get("imgType"))
                .photoDetailImgSize(map.get("imgSize"))
                .photoDetailImgSrc(map.get("imgSrc"))
                .photoBoard(photoBoard)
                .build();

        photoBoardDetailImgRepository.save(photoBoardDetailImg);

        return uploadMsg;
    }

    @Override
    public Resource getMainImg(String imgName) {
        Resource resource = new FileSystemResource(uploadDir+"\\"+imgName);
        return resource;
    }

    @Override
    public Resource getDetailImg(String imgName) {
        Resource resource = new FileSystemResource(uploadDir+"/detail"+"\\"+imgName);
        return resource;
    }

    @Override
    public String alterPhotoBoard(Long photoBoardSeq, PhotoBoardDTO photoBoardDTO) {
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq).orElseThrow(()-> new GlobalException(ErrorCode.NOTFOUND_PHOTOBOARD));
        List<String> interests = photoBoardDTO.getInterestCategory();

        // 기존 관심목록 삭제
        if(!interests.isEmpty()){
            photoBoardInterestRepository.deleteByPhotoBoardSeq(photoBoard.getPhotoBoardSeq());

            // 새로운 관심사 목록을 추가
            for(String i : interests){
                Interest interest = interestRepository.findByInterestCategory(i);
                PhotoBoardInterest saveInterest = new PhotoBoardInterest(interest,photoBoard);
                photoBoard.getPhotoBoardInterestList().add(saveInterest);
            }

        }

        if (photoBoardDTO.getPhotoBoardTitle() != null && !photoBoardDTO.getPhotoBoardTitle().trim().isEmpty()) {
            photoBoard.setPhotoBoardTitle(photoBoardDTO.getPhotoBoardTitle());
        } else {
            photoBoard.setPhotoBoardTitle(photoBoard.getPhotoBoardTitle());
        }

        return alterMsg;
    }

    @Override
    public String deletePhotoBoard(Long photoBoardSeq) {
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq).orElseThrow(()-> new GlobalException(ErrorCode.NOTFOUND_PHOTOBOARD));
        photoBoardRepository.delete(photoBoard);
        return deleteMsg;
    }

    @Override
    public String deleteMainlImg(Long photoBoardSeq) {
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq).orElseThrow(()-> new GlobalException(ErrorCode.NOTFOUND_PHOTOBOARD));

        photoBoard.setPhotoMainImgName("");
        photoBoard.setPhotoMainImgSize("");
        photoBoard.setPhotoMainImgSrc("");
        photoBoard.setPhotoMainImgType("");

        return deleteMsg;
    }

    @Override
    public String deleteDetailImg(Long photoDetailImgSeq) {
        PhotoBoardDetailImg photoBoardDetailImg = photoBoardDetailImgRepository.findById(photoDetailImgSeq).orElseThrow(()-> new GlobalException(ErrorCode.NOTFOUND_PHOTOBOARD));
        photoBoardDetailImgRepository.delete(photoBoardDetailImg);
        return deleteMsg;
    }

}
