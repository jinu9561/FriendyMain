package web.mvc.service.users;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.mvc.dto.user.JellyTransactionDTO;
import web.mvc.dto.user.RefundDTO;
import web.mvc.entity.user.JellyTransaction;
import web.mvc.entity.user.UserDetail;
import web.mvc.entity.user.Users;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.user.JellyTrasactionRepository;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.user.JellyTransctionSeriveImpl;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@Transactional
@Slf4j
public class JellyTransctionSeriveTest {

    @Autowired
    private JellyTransctionSeriveImpl jellyTransctionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JellyTrasactionRepository jellyTrasactionRepository;

    private Long userSeq;

    @BeforeEach
    public void setUp() {
        UserDetail userDetail = new UserDetail();
        userDetail.setUserJelly(0);

        Users user = new Users();
        user.setUserDetail(userDetail);
        user.setJellyTransactionList(new ArrayList<>());

        userRepository.save(user);
        userSeq = user.getUserSeq();
    }

    @Test
    public void 결제_테스트() {
        JellyTransactionDTO jellyTransactionDTO = new JellyTransactionDTO();
        jellyTransactionDTO.setJellyAmount("10");

        String result = jellyTransctionService.payment(userSeq, jellyTransactionDTO);

        log.info("{}", result);
    }

    @Test
    public void 젤리_추가_테스트() {
        JellyTransactionDTO jellyTransactionDTO = new JellyTransactionDTO();
        jellyTransactionDTO.setJellyAmount("2");

        String result = jellyTransctionService.addJelly(userSeq, jellyTransactionDTO);

        log.info("{}", result);
    }

    @Test
    public void 젤리_사용_테스트() {
        Users user = userRepository.findById(userSeq).get();
        user.getUserDetail().setUserJelly(30);
        userRepository.save(user);

        JellyTransactionDTO jellyTransactionDTO = new JellyTransactionDTO();
        jellyTransactionDTO.setJellyAmount("20");

        String result = jellyTransctionService.useJelly(userSeq, jellyTransactionDTO);

        log.info("{}", result);
    }

    @Test
    public void 환불_테스트() {
        Users user = userRepository.findById(userSeq).get();
        JellyTransaction jellyTransaction = JellyTransaction.builder()
                .user(user)
                .build();
        jellyTrasactionRepository.save(jellyTransaction);

        RefundDTO refundDTO = new RefundDTO();
        refundDTO.setTransactionSeq(jellyTransaction.getTransactionSeq());
        refundDTO.setRefundReason("Test reason");

        String result = jellyTransctionService.refund(refundDTO);

        log.info("환불 신청이 완료 되었습니다. 관리자의 안내를 기다려주세요.", result);
    }

    @Test
    public void 환불_트랜잭션_없음_테스트() {
        RefundDTO refundDTO = new RefundDTO();
        refundDTO.setTransactionSeq(999L); // 존재하지 않는 ID

        try {
            jellyTransctionService.refund(refundDTO);
        } catch (GlobalException e) {
            log.info("환불 요청 중 트랜잭션을 찾을 수 없음: {}", e.getMessage());
        }
    }
}
