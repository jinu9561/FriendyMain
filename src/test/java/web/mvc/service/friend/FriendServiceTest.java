package web.mvc.service.friend;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import web.mvc.entity.friend.FriendList;
import web.mvc.entity.friend.FriendRequest;
import web.mvc.entity.user.Users;
import web.mvc.repository.friend.FriendListRepository;
import web.mvc.repository.friend.FriendRequestRepository;
import web.mvc.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Commit
@Slf4j
public class FriendServiceTest {

    @Autowired
    FriendListRepository friendListRepository;
    @Autowired
    FriendRequestRepository friendRequestRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendService friendService;

    private Users sender;
    private Users receiver;

    @BeforeEach
    void setUp() {
        // Test Users 생성
        sender = new Users();
        sender.setUserId("sender123");
        sender.setUserName("Sender Name");
        sender.setEmail("sender@example.com");
        userRepository.save(sender);

        receiver = new Users();
        receiver.setUserId("receiver123");
        receiver.setUserName("Receiver Name");
        receiver.setEmail("receiver@example.com");
        userRepository.save(receiver);
    }

    @Test
    void 친구요청_전송_테스트() {
        FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
        log.info("Friend Request: {}", friendRequest);
    }

    // @Test
    // void 친구요청_수락_테스트() {
    //     FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
    //     friendService.acceptFriendRequest(friendRequest.getFriendRequestSeq(), receiver);

    //     Optional<FriendList> friendship = friendListRepository.findFriendship(sender, receiver);
    //     if (friendship.isPresent()) {
    //         log.info("Friendship established: {}", friendship.get());
    //     } else {
    //         log.info("Friendship not found");
    //     }
    // }

    @Test
    void 친구요청_거절_테스트() {
        FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
        friendService.rejectFriendRequest(friendRequest.getFriendRequestSeq(), receiver);

        Optional<FriendRequest> rejectedRequest = friendRequestRepository.findById(friendRequest.getFriendRequestSeq());
        if (rejectedRequest.isPresent()) {
            log.info("Friend Request rejected: {}", rejectedRequest.get());
        } else {
            log.info("Friend Request not found");
        }
    }

    @Test
    void 친구삭제_테스트() {
        FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
        friendService.acceptFriendRequest(friendRequest.getFriendRequestSeq(), receiver);

        friendService.deleteFriend(sender, receiver);

        Optional<FriendList> friendship = friendListRepository.findFriendship(sender, receiver);
        if (friendship.isPresent()) {
            log.info("Friendship still exists: {}", friendship.get());
        } else {
            log.info("Friendship deleted");
        }
    }

    // @Test
    // void 친구차단_테스트() {
    //     FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
    //     friendService.acceptFriendRequest(friendRequest.getFriendRequestSeq(), receiver);

    //     friendService.blockFriend(sender, receiver);

    //     Optional<FriendList> blockedFriendship = friendListRepository.findFriendship(sender, receiver);
    //     if (blockedFriendship.isPresent()) {
    //         log.info("Friendship blocked: {}", blockedFriendship.get());
    //     } else {
    //         log.info("Friendship not found");
    //     }
    // }

    @Test
    void 모든친구_가져오기_테스트() {
        FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
        friendService.acceptFriendRequest(friendRequest.getFriendRequestSeq(), receiver);

        List<FriendList> friends = friendService.getAllFriends(sender);
        log.info("All Friends: {}", friends);
    }
}
