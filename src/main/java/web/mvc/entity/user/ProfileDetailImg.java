package web.mvc.entity.user;


import jakarta.persistence.*;
import lombok.*;
import web.mvc.enums.users.ImgStatus;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDetailImg {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "profile_detailImg_seq")
    @SequenceGenerator(name ="profile_detailImg_seq" , allocationSize = 1 , sequenceName = "profile_detailImg_seq")
    private Long profileDetailImgSeq;
    @Column(length = 100)
    private String profileDetailImgName;
    @Column(length = 300)
    private String profileDetailImgSrc;
    @Column(length = 10)
    private String profileDetailImgType;
    @Column(length = 100)
    private String profileDetailImgSize;
    private ImgStatus imgStatus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_seq")  // 외래 키를 지정
    private Profile profile;

    public ProfileDetailImg(Profile profile) {
        this.profile = profile;
    }


}
