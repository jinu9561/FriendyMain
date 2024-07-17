package web.mvc.entity.place;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDetailImg {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_detail_img_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "place_detail_img_seq", name = "place_detail_img_seq")
    private Long placeDetailImgSeq;
    @Column(length = 300)
    private String placeDetailImgSrc;
    @Column(length = 10)
    private String placeDetailImgType;
    @Column(length = 100)
    private String placeDetailImgSize;
    @Column(length = 100)
    private String placeDetailImgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_seq")
    private PlaceRecommendation placeRecommendation;

    public PlaceDetailImg(PlaceRecommendation placeRecommendation) {
        this.placeRecommendation = placeRecommendation;
    }
}
