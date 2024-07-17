package web.mvc.dto.user;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetupRecordDTO {

    private Long meetUpRecordSeq;
    private LocalDateTime userRegDate;

}
