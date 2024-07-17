package web.mvc.enums.event;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EventStatus {

    // 0이면 사용가능 , 1이면 불가능
    ABLE(0),DISABLE(1);

    private final int flag;
}
