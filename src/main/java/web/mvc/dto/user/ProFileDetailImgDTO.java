package web.mvc.dto.user;


import lombok.*;
import web.mvc.enums.users.ImgStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProFileDetailImgDTO {

    private Long profileDetailImgSeq;
    private String profileDetailImgName;
    private String profileDetailImgSrc;
    private String profileDetailImgType;
    private String profileDetailImgSize;
    private ImgStatus imgStatus;

}
