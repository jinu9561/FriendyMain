package web.mvc.service.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.mvc.dto.user.*;
import web.mvc.entity.user.Profile;
import web.mvc.entity.user.ProfileDetailImg;
import web.mvc.entity.user.UserDetail;
import web.mvc.entity.user.Users;
import web.mvc.exception.common.GlobalException;
import web.mvc.exception.common.ErrorCode;
import web.mvc.repository.user.ProfileDetailImgRepository;
import web.mvc.repository.user.ProfileRepository;
import web.mvc.repository.user.UserDetailRepository;
import web.mvc.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final ProfileRepository profileRepository;
    private final ProfileDetailImgRepository profileDetailImgRepository;

    private String resultMsg ="변경이 완료되었습니다.!";
    private String failMsg ="변경에 실패했습니다!";

    @Override
    public List<UsersDTO> getUsersList() {
      List<UsersDTO> list = new ArrayList<>();

      List<Users> usersList = userRepository.findAll().stream().toList();

      for (Users users : usersList) {

          String role = users.getRole();

          if(!role.equals("ROLE_ADMIN") ) {
              UserDetail userDetail = userDetailRepository.findByUserSeq(users.getUserSeq());

              UserDetailDTO userDetailDTO  = UserDetailDTO.builder()
                      .UserDetailSeq(userDetail.getUserDetailSeq())
                      .userState(userDetail.getUserState())
                      .userRegDate(userDetail.getUserRegDate())
                      .userUpdateDate(userDetail.getUserUpdateDate())
                      .userRate(userDetail.getUserRate())
                      .userJelly(userDetail.getUserJelly())
                      .lastLoginDate(userDetail.getLastLoginDate())
                      .build();

              UsersDTO usersDTO = UsersDTO.builder()
                      .userSeq(users.getUserSeq())
                      .userId(users.getUserId())
                      .userPwd(users.getUserPwd())
                      .userName(users.getUserName())
                      .nickName(users.getNickName())
                      .birth(users.getBirth().toString())
                      .address(users.getAddress())
                      .email(users.getEmail())
                      .phone(users.getPhone())
                      .country(users.getCountry())
                      .userDetail(userDetailDTO)
                      .gender(users.getGender())
                      .build();

              list.add(usersDTO);
          }

      }
      return list;
    }

    @Override
    public String alterUserDetail(Long userSeq,UserDetailDTO userDetailDTO) {
        Users user = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));

        user.getUserDetail().setUserState(userDetailDTO.getUserState());
        user.getUserDetail().setUserRegDate(userDetailDTO.getUserRegDate());
        user.getUserDetail().setUserJelly(userDetailDTO.getUserJelly());

        return resultMsg;
    }

    @Override
    public List<UserProfileDTO> getProfileList() {
        return profileRepository.findAllProfiles();
    }

    @Override
    public List<UserProfileDTO> getProfileListByRegDate() {
        return profileRepository.findAllByRegDate();
    }

    @Override
    public List<UserProfileDTO> getProfileListByUpdate() {
        return profileRepository.findAllByUpdate();
    }

    @Override
    public List<UserProfileDTO> getProfileListByLastLogin() {
        return profileRepository.findAllByLastLogin();
    }

    @Override
    public List<UserProfileDTO> getProfileListByUserRate() {
        return profileRepository.findAllByUserRate();
    }

    @Override
    public List<UserProfileDetailDTO> getProfileDetail(Long userSeq) {
        return profileRepository.findUserProfileDetails(userSeq);
    }

    @Override
    public String alterProfileState(UserProfileDTO userProfileDTO) {
        Users user = userRepository.findById(userProfileDTO.getUserSeq()).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));
        Profile profile = user.getProfile();

        // 닉네임
        if(userProfileDTO.getNickName() != null && !userProfileDTO.getNickName().trim().isEmpty()) {
            if(!user.getNickName().equals(userProfileDTO.getNickName())) {
                user.setNickName(userProfileDTO.getNickName());
            }
        } else {
            user.setNickName(user.getNickName());
        }

        // 평점
        if(userProfileDTO.getUserRate() != 0 ) {
            if(user.getUserDetail().getUserRate() != userProfileDTO.getUserRate()) {
                user.getUserDetail().setUserRate(userProfileDTO.getUserRate());
            }
        } else {
            user.getUserDetail().setUserRate(user.getUserDetail().getUserRate());
        }

        // 자기소개
        if(userProfileDTO.getIntroduce() != null && !userProfileDTO.getIntroduce().trim().isEmpty()) {
            if(!profile.getIntroduce().equals(userProfileDTO.getIntroduce())) {
                profile.setIntroduce(userProfileDTO.getIntroduce());
            }
        } else {
            profile.setIntroduce(profile.getIntroduce());
        }

        // 메인 사진 상태
        if(userProfileDTO.getImgStatus() != null) {
            if(!profile.getImgStatus().equals(userProfileDTO.getImgStatus())) {
                profile.setImgStatus(userProfileDTO.getImgStatus());
            }
        } else {
            profile.setImgStatus(profile.getImgStatus());
        }

        // 유저 상태 변경
        if(userProfileDTO.getUserState() != null  ) {
            if(!user.getUserDetail().getUserState().equals(userProfileDTO.getUserState())) {
                user.getUserDetail().setUserState(userProfileDTO.getUserState());
            }
        } else {
            user.getUserDetail().setUserState(user.getUserDetail().getUserState());
        }


        return resultMsg;
    }

    @Override
    public String alterProfileDetail(Long userSeq,UserProfileDetailDTO userProfileDetailDTO) {

        Users user = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));
        Profile profile = user.getProfile();

        List<ProfileDetailImg> profileDetailList = profileDetailImgRepository.findByProfileSeq(profile.getProfileSeq());

        ProfileDetailImg profileDetailImg = null;


        for(ProfileDetailImg profileDetail : profileDetailList) {
            System.out.println("찾은 이미지: "+profileDetail.getProfileDetailImgName());
            System.out.println("뷰에서온 이미지: "+userProfileDetailDTO.getProfileDetailImgName());
            if(profileDetail.getProfileDetailImgName().equals(userProfileDetailDTO.getProfileDetailImgName())) {
                profileDetailImg = profileDetail;
            }
            else {
                return failMsg;
            }
        }

        if(profileDetailImg.getImgStatus() != userProfileDetailDTO.getImgStatus()){
            profileDetailImg.setImgStatus(userProfileDetailDTO.getImgStatus());
        }else{
            profileDetailImg.setImgStatus(profileDetailImg.getImgStatus());
        }


        return resultMsg;
    }

    @Override
    public UserProfileDTO getUserProfile(Long userSeq) {
        UserProfileDTO userProfileDTO = profileRepository.findProfilesByUserSeq(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));
        return userProfileDTO;
    }
}
