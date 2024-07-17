package web.mvc.entity.meetUpBoard;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MeetUpBoardDetailImg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meetup_detail_img_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "meetup_detail_img_seq", name = "meetup_detail_img_seq")
    private Long meetUpDetailImgSeq;

    // MeetUpBoard 엔티티의 meetUpDetailImgSeq 필드를 mappedBy에 설정
    @JoinColumn(name = "meetup_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private MeetUpBoard meetUpBoard;


    @Column(length = 500)
    private String meetUpDetailImgName;

    @Column(length = 200)
    private String meetUpDetailImgSrc;

    @Column(length = 30)
    private String meetUpDetailImgType;

    @Column(length = 30)
    private String meetUpDetailImgSize;
}
