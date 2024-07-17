package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.user.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    @Query("select u from UserDetail u left join fetch u.user where u.user.userSeq = ?1")
    public UserDetail findByUserSeq(Long userSeq);
}
