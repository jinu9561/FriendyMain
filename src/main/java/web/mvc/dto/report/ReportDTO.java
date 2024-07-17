package web.mvc.dto.report;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    private Long reportSeq;
    private Long senderSeq;
    private Long receiverSeq;
    private Long reportTypeSeq;
    private String reportUrl;
    private String reportDescription;
    private LocalDateTime reportRegDate;
    private int reportStatus;
    private int reportResult;

    private ReportTypeDTO reportTypeDTO;
    private String reportTypeName;

}
