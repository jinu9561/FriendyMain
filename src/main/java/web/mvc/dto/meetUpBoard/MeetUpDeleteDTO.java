package web.mvc.dto.meetUpBoard;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MeetUpDeleteDTO {
    private Long meetUpSeq;
    private int checkPwd;
}
