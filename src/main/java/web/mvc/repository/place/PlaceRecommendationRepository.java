package web.mvc.repository.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.place.PlaceRecommendation;

import java.util.List;

public interface PlaceRecommendationRepository extends JpaRepository<PlaceRecommendation, Long> {

    @Query("select p from PlaceRecommendation p where p.placeAddress like %?1% ")
    List<PlaceRecommendation> findByPlaceAddress(String address);

    @Query("select p from PlaceRecommendation p order by p.placeRegDate desc ")
    List<PlaceRecommendation> findAllByRegDate();

    @Query("select p from PlaceRecommendation p order by p.placeUpdateDate desc ")
    List<PlaceRecommendation> findAllByUpdateDate();
}
