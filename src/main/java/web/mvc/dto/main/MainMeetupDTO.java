package web.mvc.dto.main;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
@ToString
public class MainMeetupDTO {

    private Long meetUpSeq;
    private Long userSeq;
    private Long interestSeq;
    private String meetUpName;
    private String meetUpDesc;
    private int meetUpMaxEntry;
    private Date meetUpDeadLine;
    private Date meetUpUpdateTime;
    private int  meetUpStatus;


    private List<String> meetUpDetailImgName;
}
