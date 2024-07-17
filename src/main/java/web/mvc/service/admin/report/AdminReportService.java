package web.mvc.service.admin.report;

import web.mvc.dto.report.ReportDTO;
import web.mvc.entity.report.Report;

import java.util.List;

public interface AdminReportService {
    /**
     * 신고 목록 전체 출력 (관리자용)
     * */
    List<ReportDTO> findAllReportList();

    /**
     * 신고글 보기
     * */
    ReportDTO selectReportBySeq(Long reportSeq);

    /**
     * url을 타고 신고한 게시글로 이동하기 위해 신고한 게시글의 url 반환
     */
    String getReportedUrl(ReportDTO reportDTO);

    /**
     * 신고 처리상태 업데이트 (처리중 || 처리완료)
     * */
    ReportDTO updateReportStatus(ReportDTO reportDTO);

    /**
     * 신고 처리결과 업데이트 (무혐의 || 3일정지 || 영구정지)
     * */
    ReportDTO updateReportResult(ReportDTO reportDTO);

    void updateReportResult(Long reportSeq, int result);

    void updateReportedUserState(Long userId, int result);
}
