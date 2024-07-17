package web.mvc.service.admin.report;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web.mvc.dto.report.ReportDTO;
import web.mvc.dto.report.ReportTypeDTO;
import web.mvc.entity.report.Report;
import web.mvc.entity.report.ReportType;
import web.mvc.entity.user.UserDetail;
import web.mvc.entity.user.Users;
import web.mvc.enums.users.State;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.report.ReportRepository;
import web.mvc.repository.report.ReportTypeRepository;
import web.mvc.repository.user.UserDetailRepository;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.notification.NotificationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdminReportServiceImpl implements AdminReportService {

    private final ReportRepository reportRepository;
    private final ReportTypeRepository reportTypeRepository;
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final NotificationService notificationService;

    @Override
    public List<ReportDTO> findAllReportList() {
        List<Report> reports = reportRepository.findAll(); // 모든 Report 객체를 가져옵니다.
        return reports.stream()
                .map(this::convertToDTO) // 각 Report 객체를 ReportDTO로 변환
                .collect(Collectors.toList()); // 결과를 List로 수집
    }

    @Override
    public ReportDTO selectReportBySeq(Long reportSeq) {
        Report report = reportRepository.findById(reportSeq).orElseThrow(() -> new RuntimeException("Report not found"));
        return convertToDTO(report);
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

    @Override
    public String getReportedUrl(ReportDTO reportDTO) {
        String url = reportDTO.getReportUrl();

        log.info("url : {}", url);

        return "url";
    }

    @Override
    public ReportDTO updateReportStatus(ReportDTO reportDTO) {
        int rowsUpdated = reportRepository.updateReportStatus(reportDTO.getReportStatus(), reportDTO.getReportSeq());
        if (rowsUpdated == 0) {
            throw new GlobalException(ErrorCode.NOTFOUND_ID);
        }
        Report updatedReport = reportRepository.findById(reportDTO.getReportSeq())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTFOUND_ID));
        ReportDTO updatedReportDTO = convertToDTO(updatedReport);
        log.info("Updated status report: {}", updatedReportDTO);
        return updatedReportDTO;
    }

    @Override
    public ReportDTO updateReportResult(ReportDTO reportDTO) {
        int rowsUpdated = reportRepository.updateReportResult(reportDTO.getReportResult(), reportDTO.getReportSeq());
        if (rowsUpdated == 0) {
            throw new GlobalException(ErrorCode.NOTFOUND_ID);
        }
        Report updatedReport = reportRepository.findById(reportDTO.getReportSeq())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTFOUND_ID));
        ReportDTO updatedReportDTO = convertToDTO(updatedReport);
        log.info("Updated result report: {}", updatedReportDTO);

        // userDetail의 userSeq 찾아서 state 변경
        UserDetail userDetail = userDetailRepository.findByUserSeq(reportDTO.getReceiverSeq());

        if (userDetail != null) {
            State newState = getStateFromResult(reportDTO.getReportResult());
            userDetail.setUserState(newState);
            userDetailRepository.save(userDetail);
        } else {
            throw new RuntimeException("UserDetail not found for userSeq: " + reportDTO.getReceiverSeq());
        }

        // 신고자에게 알림 전송
        String message1 = "신고해 주신 ";
        Users sender = userRepository.findUserByUserSeq(reportDTO.getSenderSeq());
        Users receiver = userRepository.findUserByUserSeq(reportDTO.getReceiverSeq());
        String message2 = determineMessage(reportDTO.getReportResult(), reportDTO.getReceiverSeq());

        String message = message1 + receiver.getNickName() + message2;

        notificationService.addNotification(sender, message);
        System.out.println("@@@@@@!@#!$message = " + message);
        System.out.println("@@@@@@!@#!sender = " + sender.getUserId());

        return updatedReportDTO;
    }

    private State getStateFromResult(int result) {
        switch(result) {
            case 2: return State.TEMPORARY_STOP; // 3-day suspension
            case 3: return State.PERMANENT_STOP; // Permanent ban
            default: return State.NOMAL; // Default or other cases set as normal
        }
    }

    private String determineMessage(int result, Long receiverSeq) {
        switch(result) {
            case 1: return " 유저는 규정 위반 사실을 확인하지 못하여 징계가 어렵습니다.";
            case 2: return " 유저는 규정 위반으로 계정이 3일 정지되었습니다.";
            case 3: return " 유저는 규정 위반으로 영구 정지되었습니다.";
            default: return "신고 처리 결과를 확인할 수 없습니다.";
        }
    }

    @Override
    public void updateReportResult(Long reportSeq, int result) {
        Report report = reportRepository.findById(reportSeq)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        report.setReportResult(result);
        reportRepository.save(report);

        // 신고 결과에 따라 사용자 상태 업데이트
        updateReportedUserState(report.getReceiver().getUserSeq(), result);
    }

    @Override
    public void updateReportedUserState(Long userId, int result) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        switch(result) {
            case 1: // 무혐의
                break; // 상태 변경 없음
            case 2: // 3일 정지
                user.getUserDetail().setUserState(State.TEMPORARY_STOP);
                break;
            case 3: // 영구 정지
                user.getUserDetail().setUserState(State.PERMANENT_STOP);
                break;
            default:
                break;
        }

        userRepository.save(user);
    }
}
