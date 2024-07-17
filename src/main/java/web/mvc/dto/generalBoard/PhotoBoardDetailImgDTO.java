package web.mvc.dto.generalBoard;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PhotoBoardDetailImgDTO {

    private Long photoDetailImgSeq;
    private String photoDetailImgName;
    private String photoDetailImgSrc;
    private String photoDetailImgType;
    private String photoDetailImgSize;
}
