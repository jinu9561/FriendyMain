package web.mvc.entity.user;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import web.mvc.enums.users.State;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "User_detail_seq")
    @SequenceGenerator(name ="User_detail_seq" , allocationSize = 1 , sequenceName = "User_detail_seq")
    private Long UserDetailSeq;
    private State userState;
    @CreationTimestamp
    private LocalDateTime userRegDate;
    @UpdateTimestamp
    private LocalDateTime userUpdateDate;
    private int userRate;
    private int userJelly;
    private LocalDateTime lastLoginDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private Users user;
    @OneToMany(mappedBy = "userDetail" , cascade = CascadeType.ALL)
    private List<MeetupRecord> meetupRecord;


    public UserDetail(Users user) {
        this.user = user;
        this.meetupRecord = new ArrayList<>();
    }
}
