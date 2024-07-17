package web.mvc.service.user;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.user.ProFileDetailImgDTO;
import web.mvc.dto.user.ProfileDTO;

import java.util.Map;
import java.util.Objects;

public interface ProfileService {

    // 프로필 정보 띄우기
    public Map<String, Object> getProfile(Long userSeq);

    // 프로필 메인 사진 업로드
    public String uploadMainPicture(Long userSeq,MultipartFile file);

    // 프로필에 세부 사진 업로드
    public String uploadDetail(Long userSeq,MultipartFile file);

    // 프로필 자기소개 관심사 수정
    public String alterProfile(Long userSeq, ProfileDTO profileDTO);

    // 메인 이미지 가져오기
    public Resource getMainImg(String imgName);

    // 디테일 이미지 가져오기
    public Resource getDetailImg(String imgName);

    // 패스워드 바꾸기
    public String alterPassword(Long userSeq, ProfileDTO profileDTO);

    // 메인 이미지 삭제
    public String deleteMainImg(Long userSeq);

    // 디테일 이미지 삭제
    public String deleteDetailImg(Long profileDetailImgSeq);

}
