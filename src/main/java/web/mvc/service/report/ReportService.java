package web.mvc.service.report;

import org.springframework.stereotype.Service;
import web.mvc.dto.report.ReportDTO;
import web.mvc.dto.report.ReportTypeDTO;
import web.mvc.entity.report.Report;
import web.mvc.entity.report.ReportType;
import web.mvc.entity.user.Users;

import java.util.List;

@Service
public interface ReportService {

    /**
     * 신고하기 (유저용)
     */
    void insertReport(ReportDTO reportDTO, ReportTypeDTO reportTypeDTO);

    public List<Report> getAllReportByUser(Users user);

}
