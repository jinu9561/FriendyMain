package web.mvc.service.place;

import org.springframework.core.io.Resource;
import web.mvc.dto.place.PlaceDetailImgDTO;
import web.mvc.dto.place.PlaceRecommendationDTO;
import web.mvc.dto.user.UsersDTO;


import java.util.List;

public interface PlaceService {

    // 유저 주소에 맞는 장소 조회 -> 4개만
    public List<PlaceRecommendationDTO> getPlaceByUserAddress(UsersDTO usersDTO);


    // 장소에 대한 디테일 사진 가져오기
    public List<PlaceDetailImgDTO> getPlaceDetailImg(Long placeSeq);

    // 메인 이미지 가져오기
    public Resource getMainImg(String imgName);

    // 디테일 이미지 가져오기
    public Resource getDetailImg(String imgName);

}
