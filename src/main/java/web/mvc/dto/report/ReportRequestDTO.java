package web.mvc.dto.report;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import web.mvc.entity.report.Report;
import web.mvc.entity.report.ReportType;

@Getter
@Setter
@ToString
public class ReportRequestDTO {
    private ReportDTO report;
    private ReportTypeDTO reportType;
}
