package web.mvc.service.report;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web.mvc.dto.report.ReportDTO;
import web.mvc.dto.report.ReportTypeDTO;
import web.mvc.entity.friend.FriendRequest;
import web.mvc.entity.report.Report;
import web.mvc.entity.report.ReportType;
import web.mvc.entity.user.Users;
import web.mvc.repository.report.ReportRepository;
import web.mvc.repository.report.ReportTypeRepository;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.admin.report.AdminReportService;
import web.mvc.service.notification.NotificationService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReportServiceImpl implements ReportService{

    private final ReportRepository reportRepository;
    private final ReportTypeRepository reportTypeRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Override
    public void insertReport(ReportDTO reportDTO, ReportTypeDTO reportTypeDTO) {

        ReportType reportType = convertToEntity(reportTypeDTO);
        Report report = convertToEntity(reportDTO, reportType);

        ReportType savedReportType = reportTypeRepository.save(reportType);
        report.setReportType(reportTypeRepository.save(reportType));

        Report savedReport = reportRepository.save(report);

        log.info("savedReportType : {}", savedReportType);
        log.info("savedReport : {}", savedReport);
    }

    @Override
    public List<Report> getAllReportByUser(Users user) {
        return reportRepository.selectAllReportByUser(user);
    }

    public void updateReportStatus(Long reportSeq, int status) {
        int updated = reportRepository.updateReportStatus(status, reportSeq);
        if (updated > 0 && status == 1) {
            Report report = reportRepository.findById(reportSeq).orElseThrow(() -> new RuntimeException("Report not found"));
            sendReportStatusUpdateNotification(report);
        }
    }

    private void sendReportStatusUpdateNotification(Report report) {
        String message = "Report processing complete for report ID: " + report.getReportSeq();
        notificationService.sendNotification(report.getSender().getUserSeq(), message);
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
                .reportTypeDTO(convertToDTO(report.getReportType())) // ReportType 정보 추가
                .build();

        return reportDTO;
    }

    private ReportTypeDTO convertToDTO(ReportType reportType) {
        return ReportTypeDTO.builder()
                .reportTypeSeq(reportType.getReportTypeSeq())
                .reportType(reportType.getReportType())
                .seqByType(reportType.getSeqByType())
                .build();
    }

    private ReportType convertToEntity(ReportTypeDTO dto) {
        ReportType entity = new ReportType();
        entity.setReportTypeSeq(dto.getReportTypeSeq());
        entity.setReportType(dto.getReportType());
        entity.setSeqByType(dto.getSeqByType());
        return entity;
    }

    private Report convertToEntity(ReportDTO dto, ReportType reportType) {
        Report entity = new Report();
        entity.setReportSeq(dto.getReportSeq());
        // Set reference to Users using UserRepository
        entity.setSender(userRepository.getById(dto.getSenderSeq()));
        entity.setReceiver(userRepository.getById(dto.getReceiverSeq()));
        entity.setReportType(reportType);
        entity.setReportUrl(dto.getReportUrl());
        entity.setReportDescription(dto.getReportDescription());
        entity.setReportRegDate(dto.getReportRegDate());
        entity.setReportStatus(dto.getReportStatus());
        entity.setReportResult(dto.getReportResult());
        return entity;
    }

}
