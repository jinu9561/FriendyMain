package web.mvc.entity.generalBoard;


import jakarta.persistence.*;
import lombok.*;
import web.mvc.entity.user.Users;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoBoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_board_like_seq")
    @SequenceGenerator(name = "photo_board_like_seq", allocationSize = 1, sequenceName = "photo_board_like_seq")
    private Long photoBoardLikeSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_board_seq")
    private PhotoBoard photoBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private Users user;
}
