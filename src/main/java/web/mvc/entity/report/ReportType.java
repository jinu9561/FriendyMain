package web.mvc.entity.report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "report_type_seq")
    @SequenceGenerator(name ="report_type_seq" , allocationSize = 1 , sequenceName = "report_type_seq")
    private Long reportTypeSeq;

    private String reportType;  // 신고할 게시물의 유형 (댓글, 채팅방, 프로필,소모임게시글,커뮤니티게시글,사진게시글)

    private Long seqByType;
}
