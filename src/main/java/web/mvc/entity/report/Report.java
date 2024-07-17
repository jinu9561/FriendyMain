package web.mvc.entity.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import web.mvc.entity.report.ReportType;
import web.mvc.entity.user.Users;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_seq")
    @SequenceGenerator(name = "report_seq", allocationSize = 1, sequenceName = "report_seq")
    private Long reportSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_seq")  // 외래키 지정
    private Users sender; // 신고한 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_seq")  // 외래키 지정
    private Users receiver; // 신고당한 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_type_seq")  // 외래키 지정
    private ReportType reportType; // 신고할 게시물의 유형 (게시판 종류 || 댓글 || 채팅방 || 프로필)

    @Column(length = 500)
    private String reportUrl; // 신고할 게시물의 url (프로필 신고시에는 url null)
    @Column(length = 1000)
    private String reportDescription; // 신고할 내용
    @CreationTimestamp
    private LocalDateTime reportRegDate;

    @Column(nullable = false, columnDefinition = "int default 0") // 디폴트값 0
    private int reportStatus; // 0=처리중, 1=처리완료
    @Column(nullable = false, columnDefinition = "int default 0") // 디폴트값 0
    private int reportResult; // 0=신고 미적용, 1=3일정지, 2=영구정지
}
