package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.user.Interest;



public interface InterestRepository extends JpaRepository<Interest, Long> {


    @Query("select i from Interest  i where  i.interestCategory = ?1")
    public Interest findByInterestCategory(String interestCategory);

}
