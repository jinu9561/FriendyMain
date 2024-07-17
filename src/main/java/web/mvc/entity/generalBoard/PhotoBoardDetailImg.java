package web.mvc.entity.generalBoard;

import jakarta.persistence.*;
import lombok.*;
import web.mvc.entity.generalBoard.PhotoBoard;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoBoardDetailImg {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "photoBoardDetailImgSeq")
    @SequenceGenerator(name ="photoBoardDetailImgSeq" , allocationSize = 1 , sequenceName = "photoBoardDetailImgSeq")
    private Long photoDetailImgSeq;
    @Column(length = 100)
    private String photoDetailImgName;
    @Column(length = 300)
    private String photoDetailImgSrc;
    @Column(length = 10)
    private String photoDetailImgType;
    @Column(length = 100)
    private String photoDetailImgSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_board_seq")  // 외래 키를 지정
    private PhotoBoard photoBoard;

    public PhotoBoardDetailImg(PhotoBoard photoBoard) {
        this.photoBoard = photoBoard;
    }

}
