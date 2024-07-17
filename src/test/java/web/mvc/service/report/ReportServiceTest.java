package web.mvc.service.report;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import web.mvc.controller.admin.report.AdminReportController;
import web.mvc.controller.report.ReportController;
import web.mvc.entity.report.Report;
import web.mvc.entity.report.ReportType;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.report.ReportRepository;
import web.mvc.repository.report.ReportTypeRepository;
import web.mvc.service.admin.report.AdminReportService;
import web.mvc.service.user.UserService;

import java.util.List;

@SpringBootTest
@Transactional
@Commit
@Slf4j
public class ReportServiceTest {

    @Autowired
    ReportRepository reportRepository;
    @Autowired
    ReportTypeRepository reportTypeRepository;

    @Autowired
    ReportService reportService;
    @Autowired
    AdminReportService adminReportService;
    @Autowired
    UserService userService;

    @Autowired
    ReportController reportController;
    @Autowired
    AdminReportController adminReportController;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 설정
        ReportType reportType = new ReportType();
        reportType.setReportType("user"); // 신고할 게시물의 유형 설정 (예: 유저)
        reportType.setSeqByType(2L); // 임의의 seqByType 설정

        Report report = new Report();
        report.setReportStatus(1); // 상태 설정
        report.setReportUrl("https://www.naver.com/");
        report.setReportType(reportType); // 연관 관계 설정

        reportTypeRepository.save(reportType);
        reportRepository.save(report);
    }

    @Test
    void 신고하기() {
        // 테스트에 필요한 객체 생성
        ReportType reportType = new ReportType();
        reportType.setReportType("user"); // 신고할 게시물의 유형 설정 (예: 채팅방)
        reportType.setSeqByType(2L); // 임의의 seqByType 설정

        Report report = new Report();
        report.setReportStatus(1); // 상태 설정
        report.setReportUrl("https://www.naver.com/");
        report.setReportType(reportType); // 연관 관계 설정

        ReportType savedReportType = reportTypeRepository.save(reportType);
        Report savedReport = reportRepository.save(report);

        log.info("savedReportType: {}", savedReportType);
        log.info("savedReport: {}", savedReport);
    }

    @Test
    void 신고목록전체출력() {
        List<Report> list = reportRepository.findAll();
        log.info("신고목록전체출력 - 신고 목록 크기: {}", list.size());

        list.forEach(r -> log.info("@@@신고 Seq: {}", r.getReportSeq()));
    }

    @Test
    void 신고게시글_개별_출력() {
        Long reportSeq = 1L; // 테스트에 사용할 reportSeq 설정
        Report selectedReport = reportRepository.findById(reportSeq)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTFOUND_ID));

        log.info("신고게시글_개별_출력: {}", selectedReport);
    }
}
