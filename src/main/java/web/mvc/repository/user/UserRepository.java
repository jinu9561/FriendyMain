package web.mvc.repository.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.user.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("select u from Users u where u.userId = ?1")
    public Users findUserByUserId(String userId);

    @Query("select u from Users u where u.email = ?1")
    public Users findUserByEmail(String email);

    @Query("select u from Users u where u.phone = ?1")
    public Users findUserByPhone(String phone);

    @Query("select u from Users u where u.nickName = ?1")
    public Users findUserByNickName(String nickName);

    @Query("select u from Users u where u.userSeq = ?1") // 진우가 작성함
    public Users findUserByUserSeq(Long userSeq);
}
