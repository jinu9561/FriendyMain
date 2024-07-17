package web.mvc.service.chatting;

import web.mvc.dto.chat.NoticePostDTO;
import web.mvc.entity.chatting.NoticePost;

import java.util.List;

public interface NoticePostService {

    void createNoticePost(NoticePostDTO noticePostDTO);

    List<NoticePostDTO> selectAllListByRoomId(String roomId);

    NoticePost selectBySeq(Long noticePostSeq);

    void deleteBySeq(Long noticePostSeq);


}
