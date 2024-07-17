package web.mvc.repository.report;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.mvc.entity.report.Report;
import web.mvc.entity.user.Users;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    /**
     * 신고상태 변경
     */
    @Modifying
    @Transactional
    @Query("update Report r set r.reportStatus = :reportStatus where r.reportSeq = :reportSeq")
    int updateReportStatus( @Param("reportStatus") int reportStatus, @Param("reportSeq") Long reportSeq );

    /**
     * 신고결과 변경
     */
    @Modifying
    @Transactional
    @Query("update Report r set r.reportResult = :reportResult where r.reportSeq = :reportSeq")
    int updateReportResult( @Param("reportResult") int reportResult, @Param("reportSeq") Long reportSeq );

    /**
     * 신고결과 변경
     */
    @Modifying
    @Transactional
    @Query("SELECT r FROM Report r WHERE r.sender = :sender")
    List<Report> selectAllReportByUser(@Param("sender") Users sender );

}
