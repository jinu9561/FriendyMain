package web.mvc.repository.friend;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.mvc.entity.friend.FriendRequest;
import web.mvc.entity.user.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    /**
     * 친구 요청하기 (save)
     * (FRIEND_REQUEST 테이블 조회해서
     *  sender_seq와 receiver_seq이 모두 존재하면서
     *  request_status = 2(거절)가 아닌 테이블이 존재하면 친구요청 불가능)
     *  (요청 대기상태거나 이미 친구이면 요청 불가능)
     * */

    /**
     * 친구 요청 여부 확인 메서드
     * @param sender 보내는 유저
     * @param receiver 받는 유저
     * @return 친구 요청 객체
     */
    @Query("SELECT fr FROM FriendRequest fr WHERE fr.sender = :sender AND fr.receiver = :receiver AND fr.requestStatus != 2")
    Optional<FriendRequest> findFriendRequest(@Param("sender") Users sender, @Param("receiver") Users receiver);

    /**
     * 요청 수락
     * @param friendRequestSeq 친구 요청 ID
     */
    @Transactional
    @Modifying
    @Query("UPDATE FriendRequest fr SET fr.requestStatus = 1 WHERE fr.friendRequestSeq = :friendRequestSeq")
    void acceptFriendRequest(@Param("friendRequestSeq") Long friendRequestSeq);

    /**
     * 요청 거절
     * @param friendRequestSeq 친구 요청 ID
     */
    @Transactional
    @Modifying
    @Query("UPDATE FriendRequest fr SET fr.requestStatus = 2 WHERE fr.friendRequestSeq = :friendRequestSeq")
    void rejectFriendRequest(@Param("friendRequestSeq") Long friendRequestSeq);

    /**
     * 수신자별로 친구 요청을 가져옴
     * */
    @Query("SELECT fr FROM FriendRequest fr WHERE fr.receiver = :receiver AND fr.requestStatus = 0")
    List<FriendRequest> findAllByReceiver(@Param("receiver") Users receiver);

}
