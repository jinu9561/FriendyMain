package web.mvc.dto.meetUpBoard;


import lombok.*;
import web.mvc.dto.user.UserDetailDTO;
import web.mvc.enums.users.Classification;
import web.mvc.enums.users.Gender;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersRequestDTO {

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
