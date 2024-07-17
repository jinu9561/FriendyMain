package web.mvc.controller.admin.report;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.report.ReportDTO;
import web.mvc.dto.report.ReportTypeDTO;
import web.mvc.entity.report.Report;
import web.mvc.service.admin.report.AdminReportService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/report")
@Slf4j
public class AdminReportController {

    private final AdminReportService adminReportService;
//
    /**
     * 신고 목록 전체 출력 (관리자용)
     * */
    @GetMapping("/")
    public ResponseEntity<List<ReportDTO>> getReportList() {
        List<ReportDTO> reportList = adminReportService.findAllReportList();

        return ResponseEntity.status(HttpStatus.OK).body(reportList);
    }

    /**
     * 신고글 보기
     */
    @GetMapping("/{reportSeq}")
    public ResponseEntity<ReportDTO> getReportBySeq(@PathVariable Long reportSeq) {
        ReportDTO report = adminReportService.selectReportBySeq(reportSeq);
        return ResponseEntity.status(HttpStatus.OK).body(report);
    }

    /**
     * URL을 타고 신고한 게시글로 이동
     */
    @PostMapping("/moveToUrl")
    public void moveToUrl(@RequestBody ReportDTO report, HttpServletResponse response) throws IOException {
        String url = adminReportService.getReportedUrl(report);
        response.sendRedirect(url);
    }

    /**
     * 신고 처리상태 업데이트 (처리중 || 처리완료)
     */
    @PostMapping("/updateStatus")
    public ResponseEntity<ReportDTO> updateReportStatus(@RequestBody ReportDTO ReportDTO) {
        ReportDTO updatedReport = adminReportService.updateReportStatus(ReportDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedReport);
    }

    /**
     * 신고 처리결과 업데이트 (대기중 || 무죄 || 3일정지 || 영구정지)
     */
    @PostMapping("/updateResult")
    public ResponseEntity<ReportDTO> updateReportResult(@RequestBody ReportDTO ReportDTO) {
        ReportDTO updatedReport = adminReportService.updateReportResult(ReportDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedReport);
    }
}
