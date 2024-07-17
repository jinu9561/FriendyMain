package web.mvc.service.friend;

import org.springframework.stereotype.Service;
import web.mvc.entity.friend.FriendList;
import web.mvc.entity.friend.FriendRequest;
import web.mvc.entity.user.Users;

import java.util.List;

@Service
public interface FriendService {

    /**
     * 친구 요청 보내기
     * @param sender 보내는 유저
     * @param receiver 받는 유저
     * @return 친구 요청 객체
     */
    FriendRequest sendFriendRequest(Users sender, Users receiver);

    /**
     * 친구 요청 수락
     * @param friendRequestSeq 친구 요청 ID
     * @param user 요청을 수락하는 유저
     */
    void acceptFriendRequest(Long friendRequestSeq, Users user);

    /**
     * 친구 요청 거절
     * @param friendRequestSeq 친구 요청 ID
     * @param user 요청을 거절하는 유저
     */
    void rejectFriendRequest(Long friendRequestSeq, Users user);

    /**
     * 친구 삭제
     * @param user 사용자
     * @param friendUser 친구 사용자
     */
    void deleteFriend(Users user, Users friendUser);

    /**
     * 친구 차단
     * @param user 사용자
     * @param friendUser 친구 사용자
     */
    void blockFriend(Users user, Users friendUser);

    /**
     * 친구 목록 조회
     * @param user 사용자
     * @return 친구 목록
     */
    List<FriendList> getAllFriends(Users user);

    List<FriendRequest> getFriendRequests(Users user);

    FriendRequest getFriendRequestById(Long friendRequestSeq);
}
