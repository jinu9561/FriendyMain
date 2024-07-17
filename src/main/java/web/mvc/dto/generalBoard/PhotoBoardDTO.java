package web.mvc.dto.generalBoard;

import lombok.*;
import web.mvc.dto.user.InterestDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PhotoBoardDTO {

    private Long photoBoardSeq;
    private String photoBoardTitle;
    private String photoMainImgName;
    private String photoMainImgSrc;
    private String photoMainImgType;
    private String photoMainImgSize;
    private String photoBoardPwd;
    private int photoBoardLike;

    private LocalDateTime photoBoardRegDate;
    private LocalDateTime photoBoardUpdateDate;

    private Long userSeq;

    List<PhotoBoardDetailImgDTO> photoBoardDetailImgList = new ArrayList<>();
    List<InterestDTO> interestDTOList = new ArrayList<>();

    private List<String> interestCategory;



}
