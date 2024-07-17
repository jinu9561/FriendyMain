package web.mvc.repository.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.place.PlaceDetailImg;

import java.util.List;

public interface PlaceDetailImgRepository extends JpaRepository<PlaceDetailImg, Long> {

    @Query("select pd from PlaceDetailImg pd where pd.placeRecommendation.placeSeq = ?1")
    List<PlaceDetailImg> findByPlaceSeq(long placeSeq);

}
