package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.entity.user.JellyTransaction;

public interface JellyTrasactionRepository extends JpaRepository<JellyTransaction,Long> {
}
