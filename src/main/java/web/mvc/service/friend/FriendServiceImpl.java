package web.mvc.service.friend;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web.mvc.entity.friend.FriendList;
import web.mvc.entity.friend.FriendRequest;
import web.mvc.entity.user.Users;
import web.mvc.repository.friend.FriendListRepository;
import web.mvc.repository.friend.FriendRequestRepository;
import web.mvc.service.notification.NotificationService;
import web.mvc.service.notification.SseEmitterService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FriendServiceImpl implements FriendService {

    private final FriendRequestRepository friendRequestRepository;
    private final FriendListRepository friendListRepository;
    private final NotificationService notificationService;
    private final SseEmitterService sseEmitterService;

    @Override
    public FriendRequest sendFriendRequest(Users sender, Users receiver) {
        Optional<FriendRequest> existingRequest = friendRequestRepository.findFriendRequest(sender, receiver);
        if (existingRequest.isPresent()) {
            throw new IllegalStateException("이미 친구 요청을 보냈거나 수락 대기 중입니다.");
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setRequestStatus(0); // 대기 상태

        FriendRequest savedRequest = friendRequestRepository.save(friendRequest);

        // 수신자에게 알림 추가
        String message = sender.getUserName() + "님이 친구 요청을 보냈습니다.";
        notificationService.addNotification(receiver, message);
//        notificationService.sendNotification(message);  // SSE 클라이언트에게 알림 전송

        return savedRequest;
    }

    @Override
    public void acceptFriendRequest(Long friendRequestSeq, Users user) {
        Optional<FriendRequest> friendRequestOpt = friendRequestRepository.findById(friendRequestSeq);
        if (friendRequestOpt.isPresent()) {
            FriendRequest friendRequest = friendRequestOpt.get();
            Users sender = friendRequest.getSender();
            Users receiver = friendRequest.getReceiver();

            if (receiver.getUserSeq() != user.getUserSeq()) {
                throw new IllegalStateException("이 친구 요청을 수락할 권한이 없습니다.");
            }

            // 친구 요청 수락 상태로 변경
            friendRequest.setRequestStatus(1); // 수락 상태
            friendRequestRepository.save(friendRequest);

            // 친구 관계 저장
            FriendList friendList1 = new FriendList();
            friendList1.setUser(sender);
            friendList1.setFriendUser(receiver);
            friendList1.setFriendStatus(0); // 친구 상태

            // user와 receiver 바꿔서 저장
            FriendList friendList2 = new FriendList();
            friendList2.setUser(receiver);
            friendList2.setFriendUser(sender);
            friendList2.setFriendStatus(0); // 친구 상태

            friendListRepository.save(friendList1);
            friendListRepository.save(friendList2);

            // 수락 알림을 sender에게 추가 및 전송
            String message = receiver.getUserId() + "님이 친구 요청을 수락했습니다.";
            notificationService.addNotification(sender, message);
//            notificationService.sendNotification(message); // SSE 클라이언트에게 알림 전송

        } else {
            throw new IllegalStateException("친구 요청을 찾을 수 없습니다.");
        }
    }


    @Override
    public void rejectFriendRequest(Long friendRequestSeq, Users user) {
        Optional<FriendRequest> friendRequestOpt = friendRequestRepository.findById(friendRequestSeq);
        if (friendRequestOpt.isPresent()) {
            FriendRequest friendRequest = friendRequestOpt.get();
            Users receiver = friendRequest.getReceiver();

            if (receiver.getUserSeq() != user.getUserSeq()) {
                throw new IllegalStateException("이 친구 요청을 거절할 권한이 없습니다.");
            }

            // 친구 요청 거절 상태로 변경
            friendRequest.setRequestStatus(2); // 거절 상태
            friendRequestRepository.save(friendRequest);

        } else {
            throw new IllegalStateException("친구 요청을 찾을 수 없습니다.");
        }
    }

    @Override
    public void deleteFriend(Users user, Users friendUser) {
        friendListRepository.deleteFriendship(user, friendUser);
    }

    @Override
    public void blockFriend(Users user, Users friendUser) {
        friendListRepository.blockFriend(user, friendUser);
    }

    @Override
    public List<FriendList> getAllFriends(Users user) {
        return friendListRepository.findAllFriends(user.getUserSeq());
    }

    @Override
    public List<FriendRequest> getFriendRequests(Users user) {
        return friendRequestRepository.findAllByReceiver(user);
    }

    @Override
    public FriendRequest getFriendRequestById(Long friendRequestSeq) {
        return friendRequestRepository.findById(friendRequestSeq).orElseThrow(() -> new IllegalStateException("친구 요청을 찾을 수 없습니다."));
    }
}