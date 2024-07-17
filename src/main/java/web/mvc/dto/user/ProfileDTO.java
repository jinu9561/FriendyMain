package web.mvc.dto.user;


import jakarta.persistence.Column;
import lombok.*;
import web.mvc.entity.user.Interest;
import web.mvc.entity.user.Users;
import web.mvc.enums.users.ImgStatus;

import java.awt.*;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileDTO {

    private Long profileSeq;
    private String profileMainImg;
    private String profileMainImgType;
    private String profileMainImgSize;
    private String introduce;
    private Users user;
    private ImgStatus imgStatus;

    //개인정보 수정
    private String address;
    private String phone;
    private String email;
    private String nickName;
    private String userPwd;
    private String userPwdCheck;



    private List<String> interestCategory;

}
