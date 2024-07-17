package web.mvc.service.user;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web.mvc.dto.user.JellyTransactionDTO;
import web.mvc.dto.user.RefundDTO;
import web.mvc.entity.user.JellyTransaction;
import web.mvc.entity.user.Refund;
import web.mvc.entity.user.Users;
import web.mvc.enums.users.Transaction;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.user.JellyTrasactionRepository;
import web.mvc.repository.user.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class JellyTransctionSeriveImpl implements JellyTransctionService {

    private final UserRepository userRepository;
    private final JellyTrasactionRepository jellyTrasactionRepository;


    private String payMsg = "결제가 완료 되었습니다!";
    private String refundMsg ="환불 신청이 완료 되었습니다. 관리자의 안내를 기다려주세요.";

    @Override
    public String payment(Long userSeq, JellyTransactionDTO jellyTransactionDTO) {

        Users user = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));

        JellyTransaction jellyTransaction = JellyTransaction.builder()
                .user(user)
                .jellyAmount(jellyTransactionDTO.getJellyAmount())
                .amount(jellyTransactionDTO.getAmount())
                .transactionType(jellyTransactionDTO.getTransactionType())
                .build();

        user.getJellyTransactionList().add(jellyTransaction);

        int jelly = user.getUserDetail().getUserJelly();
        jelly += Integer.parseInt( jellyTransactionDTO.getJellyAmount());
        user.getUserDetail().setUserJelly(jelly);

        return payMsg;
    }

    @Override
    public String refund(RefundDTO refundDTO) {

        JellyTransaction jellyTransaction = jellyTrasactionRepository.findById(refundDTO.getTransactionSeq())
                .orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_TRANSACTION));

        Refund refund = jellyTransaction.getRefund();
        if(refund!=null){
            refund.setRefundReason(refundDTO.getRefundReason());
        }else{
            refund = Refund.builder()
                    .jellyTransaction(jellyTransaction)
                    .refundReason(refundDTO.getRefundReason())
                    .build();

            jellyTransaction.setRefund(refund);
            jellyTransaction.setTransactionType(Transaction.REFUND);
        }

        return refundMsg;
    }
}
