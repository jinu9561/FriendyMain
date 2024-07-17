package web.mvc.entity.qna;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import web.mvc.entity.user.Users;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY ,generator = "qna_seq")
    @SequenceGenerator(name ="qna_seq" , allocationSize = 1 , sequenceName = "qna_seq")
    private Long qnaSeq;
    @Column(length = 1000)
    private String qnaDesc;
    private int qnaStatus;
    @CreationTimestamp
    private LocalDateTime qnaUpdateDate;
    @CreationTimestamp
    private LocalDateTime qnaRegDate;
    @Column(length = 1000)
    private String qnaReply;

    @OneToOne(fetch = FetchType.LAZY)
    private Users user;

}
