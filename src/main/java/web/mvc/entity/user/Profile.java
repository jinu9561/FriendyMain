package web.mvc.entity.user;


import jakarta.persistence.*;
import lombok.*;
import web.mvc.enums.users.ImgStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "profile_seq")
    @SequenceGenerator(name ="profile_seq" , allocationSize = 1 , sequenceName = "profile_seq")
    private Long profileSeq;
    @Column(length = 100)
    private String profileMainImgName;
    @Column(length = 300)
    private String profileMainImgSrc;
    @Column(length = 100)
    private String profileMainImgType;
    @Column(length = 100)
    private String profileMainImgSize;
    @Column(length = 500)
    private String introduce;
    private ImgStatus imgStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")  // 외래 키를 지정
    private Users user;
    @OneToMany(mappedBy = "profile" , cascade = CascadeType.ALL)
    private List<ProfileDetailImg> profileDetailImgList;
    @OneToMany(mappedBy = "profile" , cascade = CascadeType.ALL)
    private List<ProfileInterest> profileInterestList;

    public Profile(Users user) {
        this.user = user;
        this.profileDetailImgList = new ArrayList<>();
        this.profileInterestList = new ArrayList<>();

    }

}
