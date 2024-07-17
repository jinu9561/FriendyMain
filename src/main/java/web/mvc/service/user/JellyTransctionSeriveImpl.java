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

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class JellyTransctionSeriveImpl implements JellyTransctionService {

    private final UserRepository userRepository;
    private final JellyTrasactionRepository jellyTrasactionRepository;


    private String payMsg = "결제가 완료 되었습니다!";
    private String addMsg = "2젤리를 획득했습니다!";
    private String addHostMsg = "20젤리를 획득했습니다!";
    private String useMsg = "20젤리를 사용하였습니다!";
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
    public String addJelly(Long userSeq, JellyTransactionDTO jellyTransactionDTO) {
        log.info("Adding jelly for userSeq: {}", userSeq);
        log.info("JellyTransactionDTO: {}", jellyTransactionDTO);

        Users user = userRepository.findById(userSeq).orElseThrow(() -> {
            log.error("해당 userSeq를 찾을 수 없습니다: {}", userSeq);
            return new GlobalException(ErrorCode.NOTFOUND_ID);
        });

        LocalDate today = LocalDate.now();
        if (user.getLastJellyDate() != null && user.getLastJellyDate().isEqual(today)) {
            return "";
        }

        try {
            JellyTransaction jellyTransaction = JellyTransaction.builder()
                    .user(user)
                    .jellyAmount(jellyTransactionDTO.getJellyAmount())
                    .amount(jellyTransactionDTO.getAmount())
                    .transactionType(Transaction.ADD)
                    .build();

            user.getJellyTransactionList().add(jellyTransaction);

            int jelly = user.getUserDetail().getUserJelly();
            jelly += Integer.parseInt(jellyTransactionDTO.getJellyAmount());
            user.getUserDetail().setUserJelly(jelly);

            // 마지막 젤리 추가 날짜 업데이트
            user.setLastJellyDate(today);
            userRepository.save(user);

            log.info("Jelly added successfully for userSeq: {}", userSeq);
            return addMsg;
        } catch (Exception e) {
            log.error("해당 userSeq에게 젤리 지급 실패: {}", userSeq, e);
            throw e;  // 예외를 다시 던져서 클라이언트에게 알림
        }
    }

    @Override
    public String addJellyByWritePost(Long userSeq, JellyTransactionDTO jellyTransactionDTO) {
        log.info("Adding jelly for userSeq: {}", userSeq);
        log.info("JellyTransactionDTO: {}", jellyTransactionDTO);

        Users user = userRepository.findById(userSeq).orElseThrow(() -> {
            log.error("해당 userSeq를 찾을 수 없습니다: {}", userSeq);
            return new GlobalException(ErrorCode.NOTFOUND_ID);
        });

        try {
            JellyTransaction jellyTransaction = JellyTransaction.builder()
                    .user(user)
                    .jellyAmount(jellyTransactionDTO.getJellyAmount())
                    .amount(jellyTransactionDTO.getAmount())
                    .transactionType(Transaction.ADD)
                    .build();

            user.getJellyTransactionList().add(jellyTransaction);

            int jelly = user.getUserDetail().getUserJelly();
            jelly += Integer.parseInt(jellyTransactionDTO.getJellyAmount());
            user.getUserDetail().setUserJelly(jelly);

            userRepository.save(user);

            log.info("Jelly added successfully for userSeq: {}", userSeq);
            return addMsg;
        } catch (Exception e) {
            log.error("해당 userSeq에게 젤리 지급 실패: {}", userSeq, e);
            throw e;  // 예외를 다시 던져서 클라이언트에게 알림
        }
    }

    @Override
    public String addJellyToHost(Long userSeq, JellyTransactionDTO jellyTransactionDTO) {
        log.info("Adding jelly for userSeq: {}", userSeq);
        log.info("JellyTransactionDTO: {}", jellyTransactionDTO);

        Users user = userRepository.findById(userSeq).orElseThrow(() -> {
            log.error("해당 userSeq를 찾을 수 없습니다: {} ", userSeq);
            return new GlobalException(ErrorCode.NOTFOUND_ID);
        });

        try {
            JellyTransaction jellyTransaction = JellyTransaction.builder()
                    .user(user)
                    .jellyAmount(jellyTransactionDTO.getJellyAmount())
                    .amount(jellyTransactionDTO.getAmount())
                    .transactionType(Transaction.ADD)
                    .build();

            user.getJellyTransactionList().add(jellyTransaction);

            int jelly = user.getUserDetail().getUserJelly();
            jelly += Integer.parseInt(jellyTransactionDTO.getJellyAmount());
            user.getUserDetail().setUserJelly(jelly);

            userRepository.save(user);

            log.info("Jelly added successfully for userSeq: {}", userSeq);
            return addHostMsg;
        } catch (Exception e) {
            log.error("해당 userSeq에게 젤리 지급 실패: {}", userSeq, e);
            throw e;  // 예외를 다시 던져서 클라이언트에게 알림
        }
    }

    @Override
    public String useJelly(Long userSeq, JellyTransactionDTO jellyTransactionDTO) {
        Users user = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));

        JellyTransaction jellyTransaction = JellyTransaction.builder()
                .user(user)
                .jellyAmount(jellyTransactionDTO.getJellyAmount())
                .amount(jellyTransactionDTO.getAmount())
                .transactionType(jellyTransactionDTO.getTransactionType())
                .build();

        user.getJellyTransactionList().add(jellyTransaction);

        int jelly = user.getUserDetail().getUserJelly();
        jelly -= Integer.parseInt( jellyTransactionDTO.getJellyAmount());
        user.getUserDetail().setUserJelly(jelly);

        return useMsg;
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
