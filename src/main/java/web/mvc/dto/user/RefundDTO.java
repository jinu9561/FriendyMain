package web.mvc.dto.user;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RefundDTO {

    private Long transactionSeq;
    private String refundReason;
}
