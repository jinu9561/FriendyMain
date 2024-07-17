package web.mvc.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import web.mvc.enums.users.Transaction;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JellyTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "transaction_seq")
    @SequenceGenerator(name ="transaction_seq" , allocationSize = 1 , sequenceName = "transaction_seq")
    private Long transactionSeq;
    private Transaction transactionType;
    @Column(length = 300)
    private String jellyAmount;
    @Column(length = 300)
    private String amount;
    @CreationTimestamp
    private LocalDateTime transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")  // 외래 키를 지정
    private Users user;

    @OneToOne(mappedBy = "jellyTransaction" , cascade = CascadeType.ALL)
    private Refund refund;
}
