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
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "friend_request_seq")
    @SequenceGenerator(name ="friend_request_seq" , allocationSize = 1 , sequenceName = "friend_request_seq")
    private Long friendRequestSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_seq")  // 외래키 지정
    private Users sender; // 친구 요청 보낸 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_seq")  // 외래키 지정
    private Users receiver; // 요청 받은 유저

    @Column(nullable = false, columnDefinition = "int default 0") // 디폴트값 0
    private int requestStatus; // 대기: 0, 수락:1 , 거절:2

    @CreationTimestamp
    private LocalDateTime requestRegDate;
    @CreationTimestamp
    private LocalDateTime requestUpdateDate;

}
