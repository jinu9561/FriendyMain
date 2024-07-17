package web.mvc.entity.user;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "sms_verification_seq")
    @SequenceGenerator(name ="sms_verification_seq" , allocationSize = 1 , sequenceName = "sms_verification_seq")
    private Long smsVerificationSeq;
    @Column(length = 100, unique = true)
    private String smsToken;
    @CreationTimestamp
    private LocalDateTime smsRegDate;

    @OneToOne(fetch = FetchType.LAZY)
    private Users user;

    @Transient // 엔티티로 관리 x
    private static final Random random = new Random();

    public SmsVerification(Users user) {
        this.user = user;
        this.smsToken = String.valueOf(100000 + random.nextInt(900000));
    }
}
