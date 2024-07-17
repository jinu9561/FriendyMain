package web.mvc.dto.report;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportTypeDTO {

    private Long reportTypeSeq;
    private String reportType; // 0=댓글, 1=채팅방, 2=프로필,3=소모임게시글,4=커뮤니티게시글,5=사진게시글
    private Long seqByType;

}
