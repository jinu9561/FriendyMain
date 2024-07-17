package web.mvc.enums.users;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Transaction {

    USE(0),
    CHAGE(1),
    REFUND(2);

    private final int flag;
}
