package web.mvc.dto.user;

import lombok.*;
import org.springframework.stereotype.Component;
import web.mvc.enums.users.ImgStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
@ToString
public class UserProfileDetailDTO {

    //u.userSeq, u.userName, u.nickName,u.email,u.phone, pd.profileDetailImgSrc, pd.imgStatus
    private Long userSeq;
    private String userName;
    private String nickName;
    private String email;
    private String phone;
    private String profileDetailImgName;
    private ImgStatus imgStatus;
    private Long profileSeq;
    private Long profileDetailImgSeq;



}
