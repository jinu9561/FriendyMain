package web.mvc.dto.user;


import lombok.*;
import web.mvc.enums.users.State;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {

    private Long UserDetailSeq;
    private State userState;
    private LocalDateTime userRegDate;
    private LocalDateTime userUpdateDate;
    private int userRate;
    private int userJelly;
    private String Role;
    private LocalDateTime lastLoginDate;


}
