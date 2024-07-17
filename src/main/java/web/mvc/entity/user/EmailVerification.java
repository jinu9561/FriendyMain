package web.mvc.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "email_verification_seq")
    @SequenceGenerator(name ="email_verification_seq" , allocationSize = 1 , sequenceName = "email_verification_seq")
    private Long emailVerificationSeq;
    @Column(length = 100, unique = true)
    private String emailToken;
    @CreationTimestamp
    private LocalDateTime emailRegDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")  // 외래 키를 지정
    private Users user;

    public EmailVerification(Users user) {
        this.user = user;
        this.emailToken = UUID.randomUUID().toString();
    }

}
