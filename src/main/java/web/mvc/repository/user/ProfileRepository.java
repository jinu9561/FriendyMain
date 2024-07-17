package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.dto.user.UserProfileDTO;
import web.mvc.dto.user.UserProfileDetailDTO;
import web.mvc.entity.user.Profile;
import web.mvc.entity.user.ProfileDetailImg;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("select p from Profile p where p.user.userSeq = ?1")
    public Optional<Profile> findByUserSeq(Long userSeq);

    // users, profile,userDetail을 join하여 유저의 이름, 닉네임, profile,의 정보조회
    @Query("select new web.mvc.dto.user.UserProfileDTO (u.userSeq, u.userName, u.nickName,u.email,u.phone, p.profileMainImgName,p.imgStatus,p.introduce,ud.userRegDate,ud.userRate,ud.lastLoginDate,ud.userState) " +
            "from Users u join u.profile p join u.userDetail ud ")
    List<UserProfileDTO> findAllProfiles();

    // users, profile을 join하여 유저 프로필의 세부 사진도 같이 조회
    @Query("select new web.mvc.dto.user.UserProfileDetailDTO (u.userSeq, u.userName, u.nickName,u.email,u.phone, pd.profileDetailImgName, pd.imgStatus, p.profileSeq, pd.profileDetailImgSeq) " +
            "from Users u join u.profile p join p.profileDetailImgList pd where u.userSeq= ?1")
    List<UserProfileDetailDTO> findUserProfileDetails(Long userSeq);

    @Query("select new web.mvc.dto.user.UserProfileDTO (u.userSeq, u.userName, u.nickName,u.email,u.phone, p.profileMainImgName,p.imgStatus,p.introduce,ud.userRegDate,ud.userRate,ud.lastLoginDate,ud.userState) " +
            "from Users u join u.profile p join u.userDetail ud where u.userSeq = ?1")
    Optional<UserProfileDTO> findProfilesByUserSeq(Long userSeq);

    // 등록순으로 조회
    @Query("select new web.mvc.dto.user.UserProfileDTO (u.userSeq, u.userName, u.nickName,u.email,u.phone, p.profileMainImgName,p.imgStatus,p.introduce,ud.userRegDate,ud.userRate,ud.lastLoginDate,ud.userState) " +
            "from Users u join u.profile p join u.userDetail ud order by ud.userRegDate desc ")
    List<UserProfileDTO> findAllByRegDate();

    // 프로필 수정순으로 조회
    @Query("select new web.mvc.dto.user.UserProfileDTO (u.userSeq, u.userName, u.nickName,u.email,u.phone, p.profileMainImgName,p.imgStatus,p.introduce,ud.userRegDate,ud.userRate,ud.lastLoginDate,ud.userState) " +
            "from Users u join u.profile p join u.userDetail ud order by ud.userUpdateDate desc ")
    List<UserProfileDTO> findAllByUpdate();

    // 마지막 로그인순으로 조회
    @Query("select new web.mvc.dto.user.UserProfileDTO (u.userSeq, u.userName, u.nickName,u.email,u.phone, p.profileMainImgName,p.imgStatus,p.introduce,ud.userRegDate,ud.userRate,ud.lastLoginDate,ud.userState) " +
            "from Users u join u.profile p join u.userDetail ud order by ud.lastLoginDate desc")
    List<UserProfileDTO> findAllByLastLogin();

    // 사용자 평점순으로 조회
    @Query("select new web.mvc.dto.user.UserProfileDTO (u.userSeq, u.userName, u.nickName,u.email,u.phone, p.profileMainImgName,p.imgStatus,p.introduce,ud.userRegDate,ud.userRate,ud.lastLoginDate,ud.userState) " +
            "from Users u join u.profile p join u.userDetail ud order by ud.userRate desc")
    List<UserProfileDTO> findAllByUserRate();

}
