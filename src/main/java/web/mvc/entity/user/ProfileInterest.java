package web.mvc.entity.user;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "profile_interest_seq")
    @SequenceGenerator(name ="profile_interest_seq" , allocationSize = 1 , sequenceName = "profile_interest_seq")
    private Long profileInterestSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_seq")  // 외래 키를 지정
    private Profile profile;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_seq") // fk 이름 설정
    private Interest interest;

    public ProfileInterest(Interest interest,Profile profile){
        this.interest = interest;
        this.profile = profile;
    }
    // 추가된 엔티티
}
