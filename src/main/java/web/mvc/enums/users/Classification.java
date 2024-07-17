package web.mvc.enums.users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Classification {

    // 0 내국인 1 외국인
    KOREAN(0),FOREIGNER(1);

    private final int flag;
}
