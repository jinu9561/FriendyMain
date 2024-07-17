package web.mvc.repository.chatting;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.chatting.MessageLog;
import web.mvc.entity.meetUpBoard.MeetUpBoard;

import java.util.List;

public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {



    @Query("select m from MessageLog m where m.chattingroom.chattingroomSeq =?1 order by m.chattingCreateDate asc")
    List<MessageLog> findMessageLogByRoomId (Long chattingRoomSeq);

}
