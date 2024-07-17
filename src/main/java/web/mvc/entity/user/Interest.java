package web.mvc.entity.user;


import jakarta.persistence.*;
import lombok.*;
import web.mvc.entity.generalBoard.PhotoBoard;
import web.mvc.entity.generalBoard.PhotoBoardInterest;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "interest_seq")
    @SequenceGenerator(name ="interest_seq" , allocationSize = 1 , sequenceName = "interest_seq")
    private Long interestSeq;
    @Column(length = 100)
    private String interestCategory;

    @OneToMany(mappedBy = "interest")
    List<ProfileInterest> profileInterestList;
    @OneToMany(mappedBy = "interest")
    List<PhotoBoardInterest> photoBoardInterestList;





}
