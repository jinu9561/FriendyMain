package web.mvc.repository.friend;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.mvc.entity.friend.FriendList;
import web.mvc.entity.user.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendListRepository extends JpaRepository<FriendList, Long> {

    /**
     * 친구 등록시 이미 등록한 친구인지 검사
     * (유저번호와 친구의 유저번호가 존재하는 테이블이 존재하면 등록 불가)
     **/
    @Query("SELECT fl FROM FriendList fl WHERE (fl.user = :user AND fl.friendUser = :friendUser) OR (fl.user = :friendUser AND fl.friendUser = :user)")
    Optional<FriendList> findFriendship(@Param("user") Users user, @Param("friendUser") Users friendUser);

    /**
     * 친구 삭제
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM FriendList fl WHERE (fl.user = :user AND fl.friendUser = :friendUser) OR (fl.user = :friendUser AND fl.friendUser = :user)")
    void deleteFriendship(@Param("user") Users user, @Param("friendUser") Users friendUser);

    /**
     * 친구 차단 (friend_status를 1로 변경)
     */
    @Transactional
    @Modifying
    @Query("UPDATE FriendList fl SET fl.friendStatus = 1 WHERE (fl.user = :user AND fl.friendUser = :friendUser) OR (fl.user = :friendUser AND fl.friendUser = :user)")
    void blockFriend(@Param("user") Users user, @Param("friendUser") Users friendUser);

    /**
     * 친구 목록 전체 출력 (friend_status가 1 (차단 상태)인 친구는 보이지 않음)
     */
    @Query("SELECT DISTINCT fl FROM FriendList fl WHERE fl.user.userSeq = :userSeq AND fl.friendStatus = 0")
    List<FriendList> findAllFriends(@Param("userSeq") Long userSeq);

}
