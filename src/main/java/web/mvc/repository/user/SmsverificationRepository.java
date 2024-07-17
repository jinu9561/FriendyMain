package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.user.SmsVerification;

import java.util.Optional;

public interface SmsverificationRepository extends JpaRepository<SmsVerification,Long> {

    @Query("select s from SmsVerification s left join fetch s.user where s.user.userSeq = ?1")
    public Optional<SmsVerification> findByUserSeq(Long userSeq);

    @Query("select s from SmsVerification s left join fetch s.user where s.smsToken = ?1 and s.user.userSeq = ?2")
    public  Optional<SmsVerification>  findBySmsToken(String smsToken, Long userSeq);
}
