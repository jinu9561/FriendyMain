package web.mvc.service.chatting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.mvc.dto.chat.NoticePostDTO;
import web.mvc.entity.chatting.ChattingRoom;
import web.mvc.entity.chatting.NoticePost;
import web.mvc.repository.chatting.NoticePostRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticePostServiceImpl implements NoticePostService {


    private  final NoticePostRepository noticePostRepository;
    @Override
    public void createNoticePost(NoticePostDTO noticePostDTO) {
        ChattingRoom chattingRoom = ChattingRoom.builder()
                .chattingroomSeq(noticePostDTO.getChattingRoomSeq())
                .build();

        NoticePost noticePost = NoticePost.builder()
                .noticeContent(noticePostDTO.getNoticeContent())
                .chattingroom(chattingRoom)
                .build();

        noticePostRepository.save(noticePost);
    }

    @Override
    public List<NoticePostDTO> selectAllListByRoomId(String roomId) {

        List<NoticePost> list =noticePostRepository.findAllByRoomId(roomId);
        List<NoticePostDTO> dtolist = new ArrayList<>();
        for(NoticePost noticePost : list){
         NoticePostDTO noticePostDTO = NoticePostDTO.builder()
                 .noticeContent(noticePost.getNoticeContent())
                 .noticePostSeq(noticePost.getNoticeSeq())
                 .noticeCreateDate(noticePost.getNoticeCreateDate())
                 .roomId(noticePost.getChattingroom().getRoomId())
                 .chattingRoomSeq(noticePost.getChattingroom().getChattingroomSeq())
                 .build();
            dtolist.add(noticePostDTO);
        }
        return (dtolist);
    }

    @Override
    public NoticePost selectBySeq(Long noticePostSeq) {


        noticePostRepository.updateByNoticeSeq(noticePostSeq);

        return null;
    }

    @Override
    public void deleteBySeq(Long noticePostSeq) {

        noticePostRepository.deleteByNoticeSeq(noticePostSeq);

    }
}
