package web.mvc.dto.user;


import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import web.mvc.enums.users.Classification;
import web.mvc.enums.users.Gender;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
@ToString
public class AdminDTO {

    @Value("${admin.userId}")
    private String userId;
    @Value("${admin.userPwd}")
    private String userPwd;
    @Value("${admin.userName}")
    private String userName;
    @Value("${admin.nickName}")
    private String nickName;
    @Value("${admin.birth}")
    private String birth;
    @Value("${admin.address}")
    private String address;
    @Value("${admin.email}")
    private String email;
    @Value("${admin.phone}")
    private String phone;
    @Value("${admin.country}")
    private Classification country;
    @Value("${admin.gender}")
    private Gender gender;
    @Value("${admin.role}")
    private String role;

}
