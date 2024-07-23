package web.mvc.entity.generalBoard;

import lombok.*;
import jakarta.persistence.*;
import web.mvc.entity.user.Users;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interactionSeq;

    private String interactionType;

    @ManyToOne
    @JoinColumn(name = "comm_board_seq")
    private CommunityBoard post;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private Users user;

}
