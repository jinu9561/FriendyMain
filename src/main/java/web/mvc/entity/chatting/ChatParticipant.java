package web.mvc.entity.chatting;

import jakarta.persistence.*;
import lombok.*;
import web.mvc.entity.user.Users;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChattingRoom room;

}

