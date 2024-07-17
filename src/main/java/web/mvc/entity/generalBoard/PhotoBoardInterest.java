package web.mvc.entity.generalBoard;


import jakarta.persistence.*;
import lombok.*;
import web.mvc.entity.user.Interest;
import web.mvc.entity.user.Profile;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoBoardInterest {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "photo_board_interest_seq")
    @SequenceGenerator(name ="photo_board_interest_seq" , allocationSize = 1 , sequenceName = "photo_board_interest_seq")
    private Long PhotoBoardInterestSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_board_seq")  // 외래 키를 지정
    private PhotoBoard photoBoard;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_seq") // fk 이름 설정
    private Interest interest;

    public PhotoBoardInterest(Interest interest,PhotoBoard photoBoard){
        this.interest = interest;
        this.photoBoard = photoBoard;
    }
}
