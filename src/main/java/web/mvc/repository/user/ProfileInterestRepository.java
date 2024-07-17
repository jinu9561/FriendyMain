package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.user.ProfileInterest;

import java.util.List;

public interface ProfileInterestRepository extends JpaRepository<ProfileInterest, Long> {

    @Query("select pi from ProfileInterest pi where pi.profile.profileSeq = ?1")
    public List<ProfileInterest> findByProfileSeq(Long profileSeq);

    @Modifying
    @Query("delete from ProfileInterest pi where pi.profile.profileSeq = ?1")
    public int deleteByProfileSeq(Long profileSeq);

}
