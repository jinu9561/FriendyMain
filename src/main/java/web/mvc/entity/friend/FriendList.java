package web.mvc.entity.friend;

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
public class FriendList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "friends_list_seq")
    @SequenceGenerator(name ="friends_list_seq" , allocationSize = 1 , sequenceName = "friends_list_seq")
    private Long friendsListSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")  // 외래키 지정
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_user_seq")  // 외래키 지정
    private Users friendUser; // 친구의 정보

    @Column(nullable = false, columnDefinition = "int default 0") // 디폴트값 0
    private int friendStatus; // 0 : 친구 , 1 : 차단

    @CreationTimestamp
    private LocalDateTime friendRegDate;
    @CreationTimestamp
    private LocalDateTime friendUpdateDate;
}
