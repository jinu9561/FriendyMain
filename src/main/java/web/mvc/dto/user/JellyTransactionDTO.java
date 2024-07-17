package web.mvc.dto.user;


import lombok.*;
import web.mvc.enums.users.Transaction;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JellyTransactionDTO {

    private Long transactionSeq;
    private Transaction transactionType;

    private String jellyAmount;
    private String amount;
    private String userId;
    private String transactionDate;

}
