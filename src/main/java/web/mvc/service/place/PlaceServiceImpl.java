package web.mvc.service.place;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import web.mvc.dto.place.PlaceDetailImgDTO;
import web.mvc.dto.place.PlaceRecommendationDTO;
import web.mvc.dto.user.UsersDTO;
import web.mvc.entity.place.PlaceDetailImg;
import web.mvc.entity.place.PlaceRecommendation;
import web.mvc.entity.user.Users;
import web.mvc.repository.place.PlaceDetailImgRepository;
import web.mvc.repository.place.PlaceRecommendationRepository;
import web.mvc.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRecommendationRepository placeRecommendationRepository;
    private final PlaceDetailImgRepository placeDetailImgRepository;
    private final UserRepository userRepository;

    private int maxSize = 4;
    private String defaultAddress = "경기도";
    @Value("${place.save.dir}")
    private String uploadDir;
//
    @Override
    public List<PlaceRecommendationDTO> getPlaceByUserAddress(UsersDTO usersDTO) {

        Users user = userRepository.findUserByUserId(usersDTO.getUserId());
        String address = defaultAddress;

        if(user != null){
            address = user.getAddress();
            address = extractCityFromAddress(address);
        }

        log.info(usersDTO.getAddress());

        List<PlaceRecommendation> placeRecommendationList = placeRecommendationRepository.findByPlaceAddress(address);
        log.info(placeRecommendationList.toString());
        List<PlaceRecommendationDTO> placeRecommendationDTOList = new ArrayList<>();

        for (PlaceRecommendation placeRecommendation : placeRecommendationList) {
            // 디테일 이미지 가져오기
            List<PlaceDetailImgDTO> list = this.getPlaceDetailImg(placeRecommendation.getPlaceSeq());

            PlaceRecommendationDTO placeRecommendationDTO = PlaceRecommendationDTO.builder()
                    .placeSeq(placeRecommendation.getPlaceSeq())
                    .placeName(placeRecommendation.getPlaceName())
                    .placeAddress(placeRecommendation.getPlaceAddress())
                    .placeDescription(placeRecommendation.getPlaceDescription())
                    .placeMainImg(placeRecommendation.getPlaceMainImg())
                    .placeMainImgName(placeRecommendation.getPlaceMainImgName())
                    .placeDetailImgList(list)
                    .build();

            if(placeRecommendationDTOList.size() < maxSize){
                placeRecommendationDTOList.add(placeRecommendationDTO);
            }

        }

        return placeRecommendationDTOList;
    }

    @Override
    public List<PlaceDetailImgDTO> getPlaceDetailImg(Long placeSeq) {
        List<PlaceDetailImgDTO> placeDetailImgDTOList = new ArrayList<>();
        List<PlaceDetailImg> placeDetailImgList = placeDetailImgRepository.findByPlaceSeq(placeSeq);

        for (PlaceDetailImg placeDetailImg : placeDetailImgList) {
            PlaceDetailImgDTO dto = PlaceDetailImgDTO.builder()
                    .placeDetailImgSeq(placeDetailImg.getPlaceDetailImgSeq())
                    .placeDetailImgSrc(placeDetailImg.getPlaceDetailImgSrc())
                    .placeDetailImgType(placeDetailImg.getPlaceDetailImgType())
                    .placeDetailImgSize(placeDetailImg.getPlaceDetailImgSize())
                    .placeDetailImgName(placeDetailImg.getPlaceDetailImgName())
                    .build();
            placeDetailImgDTOList.add(dto);
        }
        return placeDetailImgDTOList;
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

    // 주소 자르기
    private String extractCityFromAddress(String address) {
        int index = address.indexOf("시");
        if (index != -1) {
            return address.substring(0, index + 1);
        } else {
            return address;
        }
    }
}
