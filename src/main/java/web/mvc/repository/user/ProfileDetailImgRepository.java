package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.user.ProfileDetailImg;

import java.util.List;
import java.util.Optional;

public interface ProfileDetailImgRepository extends JpaRepository<ProfileDetailImg, Long> {

    @Query("select p from ProfileDetailImg p where p.profile.profileSeq = ?1")
    public List<ProfileDetailImg> findByProfileSeq(Long profileSeq);
}
