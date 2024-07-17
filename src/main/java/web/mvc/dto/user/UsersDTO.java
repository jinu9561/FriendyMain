package web.mvc.dto.user;


import lombok.*;
import web.mvc.enums.users.Classification;
import web.mvc.enums.users.Gender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersDTO {

    private Long userSeq;
    private String userId;
    private String userPwd;
    private String userName;
    private String nickName;

    private String birth;
    private String address;
    private String email;
    private String phone;
    private Classification country;
    private Gender gender;

    private List<String> interestCategory;
    private UserDetailDTO userDetail;



}
