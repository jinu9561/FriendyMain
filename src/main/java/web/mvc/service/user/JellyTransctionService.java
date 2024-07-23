package web.mvc.service.user;

import web.mvc.dto.user.JellyTransactionDTO;
import web.mvc.dto.user.RefundDTO;

public interface JellyTransctionService {

    // 젤리 결제
    public String payment (Long userSeq, JellyTransactionDTO jellyTransactionDT);

    // 젤리 회득
    public String addJelly (Long userSeq, JellyTransactionDTO jellyTransactionDTO);

    // 게시글 작성 시 젤리 획득
    public String addJellyByWritePost(Long userSeq, JellyTransactionDTO jellyTransactionDTO);

    // 소모임 개최 호스트에게 젤리 지급
    public String addJellyToHost(Long userSeq, JellyTransactionDTO jellyTransactionDTO);

    // 젤리 사용
    public String useJelly (Long userSeq, JellyTransactionDTO jellyTransactionDTO);

    // 참가 거절 시 젤리 환불
    public String refundJelly(Long userSeq, JellyTransactionDTO jellyTransactionDTO);

    // 젤리 환불 신청
    public String refund(RefundDTO refundDTO);
}
