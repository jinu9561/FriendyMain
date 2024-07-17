package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.entity.user.MeetupRecord;

public interface MeetUpRepository extends JpaRepository<MeetupRecord,Long> {
}
