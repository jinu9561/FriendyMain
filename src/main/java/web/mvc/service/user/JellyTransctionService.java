package web.mvc.service.user;

import web.mvc.dto.user.JellyTransactionDTO;
import web.mvc.dto.user.RefundDTO;

public interface JellyTransctionService {

    // 젤리 결제
    public String payment (Long userSeq, JellyTransactionDTO jellyTransactionDT);

    // 젤리 환불 신청
    public String refund(RefundDTO refundDTO);
}
