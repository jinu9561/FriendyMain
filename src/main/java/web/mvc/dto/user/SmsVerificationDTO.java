package web.mvc.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsVerificationDTO {

    private Long smsVerificationSeq;
    private String smsToken;
    private String phoneNumber;

    private String userId;

}
