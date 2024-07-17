package web.mvc.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "refund_seq")
    @SequenceGenerator(name ="refund_seq" , allocationSize = 1 , sequenceName = "refund_seq")
    private Long refundSeq;

    @Column(length = 300)
    private String refundReason;

    @CreationTimestamp
    private LocalDateTime refundRegDate;
    @UpdateTimestamp
    private LocalDateTime refundUpdateDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_seq")  // 외래 키를 지정
    private JellyTransaction jellyTransaction;
}
