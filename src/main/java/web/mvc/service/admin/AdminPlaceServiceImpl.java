package web.mvc.service.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.place.PlaceDetailImgDTO;
import web.mvc.dto.place.PlaceRecommendationDTO;
import web.mvc.entity.place.PlaceDetailImg;
import web.mvc.entity.place.PlaceRecommendation;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.place.PlaceDetailImgRepository;
import web.mvc.repository.place.PlaceRecommendationRepository;
import web.mvc.service.common.CommonService;
import web.mvc.service.place.PlaceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminPlaceServiceImpl implements AdminPlaceService{

    private final PlaceRecommendationRepository placeRecommendationRepository;
    private final PlaceDetailImgRepository placeDetailImgRepository;
    private final PlaceService placeService;
    private final CommonService commonService;

    @Value("${place.save.dir}")
    private String uploadDir;
    private String uploadMsg="등록에 성공했습니다.";
    private String alterMsg="수정에 성공했습니다.";
    private String deleteMsg="삭제에 성공했습니다";
    private String failMsg="수정에 실패했습니다.";


    @Override
    public PlaceRecommendationDTO getPlace(Long placeSeq) {
        PlaceRecommendation placeRecommendation = placeRecommendationRepository.findById(placeSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PLACE));
        List<PlaceDetailImgDTO> detailImgDTOList = placeService.getPlaceDetailImg(placeRecommendation.getPlaceSeq());

        PlaceRecommendationDTO dto = PlaceRecommendationDTO.builder()
                .placeSeq(placeRecommendation.getPlaceSeq())
                .placeName(placeRecommendation.getPlaceName())
                .placeAddress(placeRecommendation.getPlaceAddress())
                .placeDescription(placeRecommendation.getPlaceDescription())
                .placeMainImg(placeRecommendation.getPlaceMainImg())
                .placeMainImgName(placeRecommendation.getPlaceMainImgName())
                .placeDetailImgList(detailImgDTOList)
                .build();

        return  dto;
    }

    @Override
    public List<PlaceRecommendationDTO> getPlaceListByRegDate() {
        List<PlaceRecommendation> recommendations = placeRecommendationRepository.findAllByRegDate();
        List<PlaceRecommendationDTO> list = new ArrayList<>();

        for (PlaceRecommendation recommendation : recommendations) {
            List<PlaceDetailImgDTO> detailImgDTOList = placeService.getPlaceDetailImg(recommendation.getPlaceSeq());

            PlaceRecommendationDTO dto = PlaceRecommendationDTO.builder()
                    .placeSeq(recommendation.getPlaceSeq())
                    .placeName(recommendation.getPlaceName())
                    .placeAddress(recommendation.getPlaceAddress())
                    .placeDescription(recommendation.getPlaceDescription())
                    .placeMainImg(recommendation.getPlaceMainImg())
                    .placeMainImgName(recommendation.getPlaceMainImgName())
                    .placeDetailImgList(detailImgDTOList)
                    .build();
            list.add(dto);
        }

        return list;

    }

    @Override
    public List<PlaceRecommendationDTO> getPlaceListByUpdate() {

        List<PlaceRecommendation> recommendations = placeRecommendationRepository.findAllByUpdateDate();
        List<PlaceRecommendationDTO> list = new ArrayList<>();

        for (PlaceRecommendation recommendation : recommendations) {
            List<PlaceDetailImgDTO> detailImgDTOList = placeService.getPlaceDetailImg(recommendation.getPlaceSeq());

            PlaceRecommendationDTO dto = PlaceRecommendationDTO.builder()
                    .placeSeq(recommendation.getPlaceSeq())
                    .placeName(recommendation.getPlaceName())
                    .placeAddress(recommendation.getPlaceAddress())
                    .placeDescription(recommendation.getPlaceDescription())
                    .placeMainImg(recommendation.getPlaceMainImg())
                    .placeMainImgName(recommendation.getPlaceMainImgName())
                    .placeDetailImgList(detailImgDTOList)
                    .build();
            list.add(dto);
        }

        return list;
    }

    @Override
    public List<PlaceRecommendationDTO> getPlaceList() {
        List<PlaceRecommendation> recommendations = placeRecommendationRepository.findAll();
        List<PlaceRecommendationDTO> list = new ArrayList<>();

        for (PlaceRecommendation recommendation : recommendations) {
            List<PlaceDetailImgDTO> detailImgDTOList = placeService.getPlaceDetailImg(recommendation.getPlaceSeq());

            PlaceRecommendationDTO dto = PlaceRecommendationDTO.builder()
                    .placeSeq(recommendation.getPlaceSeq())
                    .placeName(recommendation.getPlaceName())
                    .placeAddress(recommendation.getPlaceAddress())
                    .placeDescription(recommendation.getPlaceDescription())
                    .placeMainImg(recommendation.getPlaceMainImg())
                    .placeMainImgName(recommendation.getPlaceMainImgName())
                    .placeDetailImgList(detailImgDTOList)
                    .build();
            list.add(dto);
        }

        return list;
    }

    @Override
    public Map<String,String> uploadePlace(PlaceRecommendationDTO placeRecommendationDTO, MultipartFile file) {

        Map<String,String> map = commonService.uploadFile(true,file,uploadDir);

        PlaceRecommendation placeRecommendation = PlaceRecommendation.builder()
                        .placeName(placeRecommendationDTO.getPlaceName())
                        .placeAddress(placeRecommendationDTO.getPlaceAddress())
                        .placeDescription(placeRecommendationDTO.getPlaceDescription())
                        .placeMainImg(map.get("imgSrc"))
                        .placeMainImgSize(map.get("imgSize"))
                        .placeMainImgType(map.get("imgType"))
                        .placeMainImgName(map.get("imgName"))
                        .build();


            placeRecommendationRepository.save(placeRecommendation);
            map.put("msg",uploadMsg);
            map.put("placeSeq",placeRecommendation.getPlaceSeq().toString());

        return map;
    }

    @Override
    public String uploadMainImg(Long placeSeq, MultipartFile file) {
        Map<String,String> map = commonService.uploadFile(true,file,uploadDir);

        PlaceRecommendation placeRecommendation = placeRecommendationRepository.findById(placeSeq)
                .orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PLACE));

        placeRecommendation.setPlaceMainImgSize(map.get("imgSrc"));
        placeRecommendation.setPlaceMainImgType(map.get("imgType"));
        placeRecommendation.setPlaceMainImgSize(map.get("imgSize"));
        placeRecommendation.setPlaceMainImgName(map.get("imgName"));

        return uploadMsg;
    }

    @Override
    public String uploadPlaceDetail(Long placeSeq,MultipartFile file) {

        Map<String,String> map = commonService.uploadFile(false,file,uploadDir);

        PlaceRecommendation placeRecommendation = placeRecommendationRepository.findById(placeSeq)
                .orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PLACE));

        PlaceDetailImg placeDetailImg = new PlaceDetailImg(placeRecommendation);

        placeDetailImg.setPlaceDetailImgSrc(map.get("imgSrc"));
        placeDetailImg.setPlaceDetailImgType(map.get("imgType"));
        placeDetailImg.setPlaceDetailImgSize(map.get("imgSize"));
        placeDetailImg.setPlaceDetailImgName(map.get("imgName"));

        placeDetailImgRepository.save(placeDetailImg);

        return uploadMsg;
    }

    @Override
    public String alterPlace(Long placeSeq,  PlaceRecommendationDTO placeRecommendationDTO){

        PlaceRecommendation placeRecommendation = placeRecommendationRepository.findById(placeSeq).orElseThrow(
                ()->new GlobalException(ErrorCode.NOTFOUND_PLACE)
        );

        placeRecommendation.setPlaceName(placeRecommendationDTO.getPlaceName());
        placeRecommendation.setPlaceAddress(placeRecommendationDTO.getPlaceAddress());
        placeRecommendation.setPlaceDescription(placeRecommendationDTO.getPlaceDescription());


        return alterMsg;
    }

    @Override
    public String deletePlace(Long placeSeq) {
        PlaceRecommendation placeRecommendation = placeRecommendationRepository.findById(placeSeq).orElseThrow(
                ()->new GlobalException(ErrorCode.NOTFOUND_PLACE)
        );


        placeRecommendationRepository.delete(placeRecommendation);

        return deleteMsg;
    }

    @Override
    public String deleteMainlImg(Long placeSeq) {
        PlaceRecommendation placeRecommendation = placeRecommendationRepository.findById(placeSeq).orElseThrow(
                ()->new GlobalException(ErrorCode.NOTFOUND_PLACE)
        );

        placeRecommendation.setPlaceMainImg("");
        placeRecommendation.setPlaceMainImgName("");
        placeRecommendation.setPlaceMainImgSize("");
        placeRecommendation.setPlaceMainImgType("");

        return deleteMsg;
    }

    @Override
    public String deleteDetailImg(Long placeDetailImgSeq) {
        PlaceDetailImg placeDetailImg = placeDetailImgRepository.findById(placeDetailImgSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PLACE));
        placeDetailImgRepository.delete(placeDetailImg);
        return deleteMsg;
    }


}
