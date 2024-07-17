package web.mvc.dto.user;



import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InterestDTO {

    private Long interestSeq;
    private String interestCategory;

}
