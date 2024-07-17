package web.mvc.controller.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import web.mvc.config.user.CustomMemberDetails;
import web.mvc.dto.friend.FriendRequestDTO;
import web.mvc.dto.report.ReportDTO;
import web.mvc.dto.report.ReportRequestDTO;
import web.mvc.entity.friend.FriendRequest;
import web.mvc.entity.report.Report;
import web.mvc.entity.report.ReportType;
import web.mvc.entity.user.Users;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.admin.report.AdminReportService;
import web.mvc.service.report.ReportService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
@Slf4j
public class ReportController {

    private final ReportService reportService;
    private final AdminReportService adminReportService;

    /**
     * 신고하기
     */
    @PostMapping("/send")
    public ResponseEntity<String> insertReport(@RequestBody ReportRequestDTO reportRequestDTO) {

        log.info("senderSeq = {}", reportRequestDTO.getReport().getSenderSeq());
        log.info("receiverSeq = {}", reportRequestDTO.getReport().getReceiverSeq());

        try {
            reportService.insertReport(reportRequestDTO.getReport(), reportRequestDTO.getReportType());
            return ResponseEntity.ok("신고가 접수되었습니다.");
        } catch (Exception e) {
            log.error("신고 접수 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("신고 처리 중 오류가 발생했습니다.");
        }
    }

    /**
     * 신고처리내용
     */
    @GetMapping("/result")
    public ResponseEntity<List<ReportDTO>> getReportResult(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();

        List<Report> reports = reportService.getAllReportByUser(userDetails.getUsers());
        List<ReportDTO> reportDTOs = reports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(reportDTOs);
    }

    private ReportDTO convertToDTO(Report report) {
        ReportDTO reportDTO = ReportDTO.builder()
                .reportSeq(report.getReportSeq())
                .senderSeq(report.getSender().getUserSeq())
                .receiverSeq(report.getReceiver() != null ? report.getReceiver().getUserSeq() : null)
                .reportTypeSeq(report.getReportType().getReportTypeSeq())
                .reportUrl(report.getReportUrl())
                .reportDescription(report.getReportDescription())
                .reportRegDate(report.getReportRegDate())
                .reportStatus(report.getReportStatus())
                .reportResult(report.getReportResult())
                .build();

        return reportDTO;
    }

}
