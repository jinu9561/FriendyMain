package web.mvc.dto.meetUpBoard;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString


public class MeetUpInviteListDTO {
    private String userNickName;
    private String email;
    private String country;
    private String gender;
    private String phone;

}
