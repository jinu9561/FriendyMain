package web.mvc.repository.chatting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.entity.chatting.NoticePost;

import java.util.List;

public interface NoticePostRepository extends JpaRepository<NoticePost, Long> {

    @Query("select n from NoticePost  n where n.chattingroom.roomId =?1 order by n.noticeCreateDate desc ")
    List<NoticePost> findAllByRoomId(String roomId);

    @Transactional
    @Modifying
    @Query("delete from NoticePost n where n.noticeSeq = ?1")
    void deleteByNoticeSeq(Long noticePostSeq);

    @Modifying
    @Transactional
    @Query("update NoticePost n set n.noticeCreateDate =current timestamp  where n.noticeSeq=?1")
    void updateByNoticeSeq(Long noticePostSeq);

}
