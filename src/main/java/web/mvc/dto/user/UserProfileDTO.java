package web.mvc.dto.user;

import lombok.*;
import org.springframework.stereotype.Component;
import web.mvc.enums.users.ImgStatus;
import web.mvc.enums.users.State;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
@ToString
public class UserProfileDTO {

    //u.userSeq, u.userName, u.nickName,u.email,u.phone, p.profileMainImg,p.imgStatus,p.introduce
    private Long userSeq;
    private String userName;
    private String nickName;
    private String email;
    private String phone;
    private String profileMainImgName;
    private ImgStatus imgStatus;
    private String introduce;
    private LocalDateTime regDate;
    private int userRate;
    private LocalDateTime lastLoginDate;
    private State userState;



}
