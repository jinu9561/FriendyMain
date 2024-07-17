package web.mvc.enums.users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Gender {
    
    // 0 남자 , 1 여자 
    MALE(0), FEMALE(1);

    private final int flag;
}
