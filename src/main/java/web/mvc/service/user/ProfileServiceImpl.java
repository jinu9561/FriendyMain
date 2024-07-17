package web.mvc.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.controller.user.ProfanityFilter;
import web.mvc.dto.user.JellyTransactionDTO;
import web.mvc.dto.user.ProfileDTO;
import web.mvc.entity.user.*;
import web.mvc.dto.user.InterestDTO;
import web.mvc.dto.user.ProFileDetailImgDTO;
import web.mvc.enums.users.ImgStatus;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.user.*;
import web.mvc.service.common.CommonService;


import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileDetailImgRepository profileDetailImgRepository;
    private final ProfileInterestRepository profileInterestRepository;
    private final CommonService commonService;
    private final PasswordEncoder passwordEncoder;
    private final InterestRepository interestRepository;

    @Value("${profile.save.dir}")
    private String uploadDir;
    private String uploadImgMsg = "사진 등록에 성공했습니다. 관리자의 승인을 기다려주세요!";
    private String alertFailMsg = "비밀번호가 일치 하지않습니다";
    private String alertMsg = "변경이 완료되었습니다";
    private String deleteMsg = "삭제가 완료되었습니다";

    @Override
    public Map<String, Object> getProfile(Long userSeq) {
        Map<String, Object> map = new HashMap<String,Object>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Users user = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));
        Profile profile = profileRepository.findByUserSeq(user.getUserSeq()).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PROFILE));

        map.put("userName", user.getUserName());
        map.put("nickName", user.getNickName());
        map.put("country",user.getCountry());
        map.put("gender",user.getGender());
        map.put("userJelly",user.getUserDetail().getUserJelly());
        map.put("introduce",profile.getIntroduce());
        map.put("profileMainImgName",profile.getProfileMainImgName());
        map.put("profileMainApprove",profile.getImgStatus());
        map.put("userState",user.getUserDetail().getUserState());
        // 매너 온도
        map.put("userRate",user.getUserDetail().getUserRate());

        // 개인정보 수정때문에 추가한 내용
        map.put("address", user.getAddress());
        map.put("phone", user.getPhone());
        map.put("email",user.getEmail());


        List<ProFileDetailImgDTO> profileDetailImgDTOList = profile.getProfileDetailImgList().stream()
                .map(img -> {
                    ProFileDetailImgDTO dto = new ProFileDetailImgDTO();
                    dto.setProfileDetailImgSeq(img.getProfileDetailImgSeq());
                    dto.setProfileDetailImgName(img.getProfileDetailImgName());
                    dto.setProfileDetailImgSrc(img.getProfileDetailImgSrc());
                    dto.setProfileDetailImgType(img.getProfileDetailImgType());
                    dto.setProfileDetailImgSize(img.getProfileDetailImgSize());
                    dto.setImgStatus(img.getImgStatus());
                    return dto;
                }).collect(Collectors.toList());
        map.put("profileDetailImgList",profileDetailImgDTOList);

        List<ProfileInterest> profileInterestList = profileInterestRepository.findByProfileSeq(profile.getProfileSeq());

        List<InterestDTO> interestDTOList = profileInterestList.stream()
                .map(profileInterest -> {
                    InterestDTO dto = new InterestDTO();
                    dto.setInterestSeq(profileInterest.getInterest().getInterestSeq());
                    dto.setInterestCategory(profileInterest.getInterest().getInterestCategory());
                    return dto;
                }).collect(Collectors.toList());
        map.put("interestList",interestDTOList);//

        List<JellyTransactionDTO> jellyTransactionDTOList = user.getJellyTransactionList().stream()
                .map(jellyTransaction -> {
                    JellyTransactionDTO dto = new JellyTransactionDTO();
                    dto.setTransactionSeq(jellyTransaction.getTransactionSeq());
                    dto.setTransactionType(jellyTransaction.getTransactionType());
                    dto.setAmount(jellyTransaction.getAmount());
                    dto.setTransactionDate(jellyTransaction.getTransactionDate().format(formatter));
                    dto.setJellyAmount(jellyTransaction.getJellyAmount());
                    return dto;
                }).toList();
        map.put("purchaseHistory",jellyTransactionDTOList);


        return map;
    }

    @Override
    public String uploadMainPicture(Long userSeq, MultipartFile file) {
        Profile profile = profileRepository.findByUserSeq(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PROFILE));
        Map<String, String>map =commonService.uploadFile(true,file,uploadDir);

        profile.setProfileMainImgName(map.get("imgName"));
        profile.setProfileMainImgSrc(map.get("imgSrc"));
        profile.setProfileMainImgSize(map.get("imgSize"));
        profile.setProfileMainImgType(map.get("imgType"));
        log.info(map.get("imgName"));
        profile.setImgStatus(ImgStatus.PENDING);

        return uploadImgMsg;
    }

    @Override
    public String uploadDetail(Long userSeq, MultipartFile file) {
        Profile profile = profileRepository.findByUserSeq(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PROFILE));
        Map<String, String>map =commonService.uploadFile(false,file,uploadDir);
        ProfileDetailImg profileDetailImg = new ProfileDetailImg(profile);

        profileDetailImg.setProfileDetailImgSrc(map.get("imgSrc"));
        profileDetailImg.setProfileDetailImgName(map.get("imgName"));
        profileDetailImg.setProfileDetailImgType(map.get("imgType"));
        profileDetailImg.setProfileDetailImgSize(map.get("imgSize"));
        profileDetailImg.setImgStatus(ImgStatus.PENDING);

        profileDetailImgRepository.save(profileDetailImg);

        return uploadImgMsg;
    }

    @Override
    public String alterProfile(Long userSeq, ProfileDTO profileDTO) {

        Users user = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));
        Profile profile = profileRepository.findByUserSeq(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PROFILE));

        log.info("profilDTO : "+profileDTO);
        List<String> interests = profileDTO.getInterestCategory();

        // 기존 관심목록 삭제
        if(!interests.isEmpty()){
            profileInterestRepository.deleteByProfileSeq(profile.getProfileSeq());

            // 새로운 관심사 목록을 추가
            for (String interestCategory : interests) {
                Interest interest = interestRepository.findByInterestCategory(interestCategory);
                if (interest == null) {
                    throw new GlobalException(ErrorCode.JSON_PROCESSING_ERROR);
                }
                ProfileInterest saveInterest = new ProfileInterest(interest, profile);
                profile.getProfileInterestList().add(saveInterest);
            }


        }

        if (profileDTO.getIntroduce() != null && !profileDTO.getIntroduce().trim().isEmpty()) {
            if (ProfanityFilter.containsProfanity(profileDTO.getIntroduce())) {
                return "자기소개에 욕설이 포함되어 있습니다.";
            } else {
                profile.setIntroduce(profileDTO.getIntroduce());
            }
        } else {
            profile.setIntroduce(profile.getIntroduce());
        }

        if (profileDTO.getAddress() != null && !profileDTO.getAddress().trim().isEmpty()) {
            user.setAddress(profileDTO.getAddress());
        } else {
            user.setAddress(user.getAddress());
        }

        if (profileDTO.getPhone() != null && !profileDTO.getPhone().trim().isEmpty()) {
            if(userRepository.findUserByPhone(profileDTO.getPhone()) == null){
                user.setPhone(profileDTO.getPhone());
            }else{
                return "등록된 전화번호입니다";
            }

        } else {
            user.setPhone(user.getPhone());
        }

        if (profileDTO.getEmail() != null && !profileDTO.getEmail().trim().isEmpty()) {
            if(userRepository.findUserByEmail(profileDTO.getEmail()) == null){
                user.setEmail(profileDTO.getEmail());
            }else{
                return "등록된 이메일입니다";
            }

        } else {
            user.setEmail(user.getEmail());
        }

        if (profileDTO.getNickName() != null && !profileDTO.getNickName().trim().isEmpty()) {
            if(userRepository.findUserByNickName(profileDTO.getNickName()) == null){
                user.setNickName(profileDTO.getNickName());
            }else{
                return "등록된 닉네임입니다";
            }

        } else {
            user.setNickName(user.getNickName());
        }

        return alertMsg;
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
    public String alterPassword(Long userSeq, ProfileDTO profileDTO) {

        Users user = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));

        if(!profileDTO.getUserPwd().equals(profileDTO.getUserPwdCheck())){
            return alertFailMsg;
        }


        // 새 비밀번호 암호화
        String encodePwd  = passwordEncoder.encode(profileDTO.getUserPwd());
        user.setUserPwd(encodePwd);

        return alertMsg;
    }

    @Override
    public String deleteMainImg(Long userSeq) {
        Profile profile = profileRepository.findByUserSeq(userSeq)
                .orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PROFILE));

        profile.setProfileMainImgSrc("");
        profile.setProfileMainImgSize("");
        profile.setProfileMainImgType("");
        profile.setProfileMainImgName("");

        profile.setImgStatus(ImgStatus.PENDING);

        return deleteMsg;
    }

    @Override
    public String deleteDetailImg(Long profileDetailImgSeq) {
        profileDetailImgRepository.deleteById(profileDetailImgSeq);
        return deleteMsg;
    }

}
