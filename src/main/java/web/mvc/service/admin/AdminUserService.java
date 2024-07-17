package web.mvc.service.admin;

import web.mvc.dto.user.UserDetailDTO;
import web.mvc.dto.user.UserProfileDTO;
import web.mvc.dto.user.UserProfileDetailDTO;
import web.mvc.dto.user.UsersDTO;


import java.util.List;

public interface AdminUserService {

    // 전체 유저 조회
    public List<UsersDTO> getUsersList();

    // 유저 상태 변경 , 보유 젤리 변경, 회원 평점 변경
    public String alterUserDetail(Long userSeq, UserDetailDTO userDetailDTO);

    // 전체 유저 프로필 조회
    public List<UserProfileDTO> getProfileList();

    // 최근 가입자순 유저 조회
    public List<UserProfileDTO> getProfileListByRegDate();

    // 프로필 수정순 유저 조회
    public List<UserProfileDTO> getProfileListByUpdate();

    // 마지막 로그인순 유저 조회
    public List<UserProfileDTO> getProfileListByLastLogin();

    // 유저 평점순 유저 조회
    public List<UserProfileDTO> getProfileListByUserRate();

    // 전체 유저 상세 프로필 조회
    public List<UserProfileDetailDTO> getProfileDetail(Long userSeq);

    // 유저 프로필 사진 상태 변경
    public String alterProfileState(UserProfileDTO userProfileDTO);

    // 유저 세부 사진 상태 변경
    public String alterProfileDetail(Long userSeq,UserProfileDetailDTO userProfileDetailDTO);

    //해당 유저 프로필 가져오기
    public UserProfileDTO getUserProfile(Long userSeq);
}
