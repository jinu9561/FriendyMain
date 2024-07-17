package web.mvc.dto.place;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceDetailImgDTO {

    private Long placeDetailImgSeq;
    private String placeDetailImgSrc;
    private String placeDetailImgType;
    private String placeDetailImgSize;
    private String placeDetailImgName;

}
