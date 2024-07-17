package web.mvc.controller.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import web.mvc.config.user.CustomMemberDetails;
import web.mvc.dto.friend.FriendListDTO;
import web.mvc.dto.friend.FriendRequestDTO;
import web.mvc.entity.friend.FriendList;
import web.mvc.entity.friend.FriendRequest;
import web.mvc.entity.user.Users;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.friend.FriendService;
import web.mvc.service.notification.NotificationService;
import web.mvc.service.user.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
@Slf4j
public class FriendController {

    private final FriendService friendService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    /**
     * 친구 요청 보내기
     */
    @PostMapping("/request")
    public ResponseEntity<?> sendFriendRequest(@RequestParam String receiverNickname) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();

        Users sender = userDetails.getUsers();
        Users receiver = userRepository.findUserByNickName(receiverNickname);

        if (receiver == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 닉네임의 사용자를 찾을 수 없습니다.");
        }

        try {
            FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
            notificationService.addNotification(receiver, "친구 요청이 왔습니다."); // 알림 추가
            return ResponseEntity.status(HttpStatus.OK).body(friendRequest);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * 친구 요청 수락
     */
    @PostMapping("/request/accept")
    public ResponseEntity<?> acceptFriendRequest(@RequestBody Map<String, Long> request) {
        Long friendRequestSeq = request.get("friendRequestSeq");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();

        Users receiver = userDetails.getUsers();

        try {
            friendService.acceptFriendRequest(friendRequestSeq, receiver);

            // 친구 요청 수락 후 sender에게 알림 전송
            FriendRequest friendRequest = friendService.getFriendRequestById(friendRequestSeq);
            Users sender = friendRequest.getSender();
            log.info("@@@@@receiver = {}", receiver);
            log.info("@@@@@sender = {}", sender);
            notificationService.addNotification(sender, receiver.getUserId() + "님이 친구 요청을 수락했습니다.");

            return ResponseEntity.status(HttpStatus.OK).body("친구 요청이 수락되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * 친구 요청 거절
     */
    @PostMapping("/request/reject")
    public ResponseEntity<?> rejectFriendRequest(@RequestBody Map<String, Long> request) {
        Long friendRequestSeq = request.get("friendRequestSeq");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();

        try {
            friendService.rejectFriendRequest(friendRequestSeq, userDetails.getUsers());
            return ResponseEntity.status(HttpStatus.OK).body("친구 요청이 거절되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * 친구 삭제
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFriend(@RequestParam Long friendUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();

        Users user = userDetails.getUsers();
        Users friendUser = new Users();
        friendUser.setUserSeq(friendUserId);

        try {
            friendService.deleteFriend(user, friendUser);
            return ResponseEntity.status(HttpStatus.OK).body("친구가 삭제되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * 친구 차단
     */
    @PostMapping("/block")
    public ResponseEntity<?> blockFriend(@RequestParam Long friendUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();

        Users user = userDetails.getUsers();
        Users friendUser = new Users();
        friendUser.setUserSeq(friendUserId);

        try {
            friendService.blockFriend(user, friendUser);
            return ResponseEntity.status(HttpStatus.OK).body("친구가 차단되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * 친구 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<List<FriendListDTO>> getAllFriends(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //시큐리티에 저장된 정보 조회
        String name = authentication.getName();//아이디

        CustomMemberDetails cu =  (CustomMemberDetails)authentication.getPrincipal();

        List<FriendList> friends = friendService.getAllFriends(cu.getUsers());
        List<FriendListDTO> friendListDTOs = friends.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        for (FriendListDTO friend : friendListDTOs) {
            log.info("FriendListDTO: {}", friend);
        }

        return ResponseEntity.status(HttpStatus.OK).body(friendListDTOs);
    }

    private FriendListDTO convertToDto(FriendList friendList) {
        return FriendListDTO.builder()
                .friendsListSeq(friendList.getFriendsListSeq())
                .userSeq(friendList.getUser().getUserSeq())
                .friendUserSeq(friendList.getFriendUser().getUserSeq())
                .nickName(friendList.getFriendUser().getNickName())
                .friendStatus(friendList.getFriendStatus())
                .friendRegDate(friendList.getFriendRegDate())
                .friendUpdateDate(friendList.getFriendUpdateDate())
                .build();
    }

    /**
     * 친구 요청 목록 조회
     */
    @GetMapping("/request/list")
    public ResponseEntity<List<FriendRequestDTO>> getAllFriendRequests(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();

        List<FriendRequest> friendRequests = friendService.getFriendRequests(userDetails.getUsers());
        List<FriendRequestDTO> friendRequestDTOs = friendRequests.stream()
                .map(this::convertToFriendRequestDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(friendRequestDTOs);
    }

    private FriendRequestDTO convertToFriendRequestDto(FriendRequest friendRequest) {
        return FriendRequestDTO.builder()
                .friendRequestSeq(friendRequest.getFriendRequestSeq())
                .senderSeq(friendRequest.getSender().getUserSeq())
                .senderName(friendRequest.getSender().getUserName())
                .receiverSeq(friendRequest.getReceiver().getUserSeq())
                .build();
    }
}