package web.mvc.entity.generalBoard;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import web.mvc.entity.user.Interest;
import web.mvc.entity.user.ProfileInterest;
import web.mvc.entity.user.Users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "photo_board_seq")
    @SequenceGenerator(name ="photo_board_seq" , allocationSize = 1 , sequenceName = "photo_board_seq")
    private Long photoBoardSeq;
    @Column(length = 100)
    private String photoBoardTitle;
    @Column(length = 100)
    private String photoMainImgName;
    @Column(length = 300)
    private String photoMainImgSrc;
    @Column(length = 100)
    private String photoMainImgType;
    @Column(length = 100)
    private String photoMainImgSize;
    private int photoBoardLike;


    @CreationTimestamp
    private LocalDateTime photoBoardRegDate;
    @UpdateTimestamp
    private LocalDateTime photoBoardUpdateDate;

    @OneToMany(mappedBy = "photoBoard" , cascade = CascadeType.ALL)
    List<PhotoBoardDetailImg> photoBoardDetailImgList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private Users user;

    @OneToMany(mappedBy = "photoBoard" , cascade = CascadeType.ALL)
    private List<PhotoBoardInterest> photoBoardInterestList = new ArrayList<>();

    @OneToMany(mappedBy = "photoBoard", cascade = CascadeType.ALL)
    private List<PhotoBoardLike> photoBoardLikeList = new ArrayList<>();










}
