package web.mvc.dto.place;

import jakarta.persistence.Column;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceRecommendationDTO {

    private Long placeSeq;
    private String placeName;
    private String placeAddress;
    private String placeDescription;
    private String placeMainImg;
    private String placeMainImgType;
    private String placeMainImgSize;
    private String placeMainImgName;

    List<PlaceDetailImgDTO> placeDetailImgList = new ArrayList<>();
}
