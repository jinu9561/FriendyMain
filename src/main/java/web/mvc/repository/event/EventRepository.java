package web.mvc.repository.event;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.event.Event;
import web.mvc.enums.event.EventStatus;

import java.time.LocalDateTime;
import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {

    //수정
    @Modifying
    @Transactional
    @Query("update Event e set e.eventContent = ?2, e.eventMainImg = ?3, e.eventMaxEntry = ?4, " +
            "e.eventName = ?5, e.eventDeadLine = ?6, e.eventStatus = ?7 where e.eventSeq = ?1")
    int updateEventBySeq(Long eventSeq, String eventContent, Long eventMainImg, Long eventMaxEntry,
                         String eventName, LocalDateTime eventDeadLine, int eventStatus);

    //삭제
    @Modifying
    @Transactional
    @Query("delete from Event e where e.eventSeq = ?1")
    int deleteByEventStatus(Long eventSeq);

    //전체검색(데드라인 순으로 검색)
    @Query("select e from Event e order by e.eventDeadLine asc")
    List<Event> findAllByEventDeadLine();

    //전체검색(등록일 순으로 검색)
    @Query("select e from Event e order by e.eventRegDate asc")
    List<Event> findAllByEventRegDate();

    //이벤트 1개 상세검색(eventSeq로 - 배너에서 사용)
    @Query("select e from Event e where e.eventSeq = ?1")
    List<Event> findEventByEventSeq(Long eventSeq);


}
