package web.mvc.enums.users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ImgStatus {

    // 0= 대기 , 1= 승인 , 2=반려
    PENDING(0),
    APPROVED(1),
    REJECTED(2);

    private final int flag;
}
