package web.mvc.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.mvc.entity.report.ReportType;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, Long> {

}
