package web.mvc.enums.users;

import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
public enum State {

    //0=대기, 1=정상, 2=인증된 사용자, 3=탈퇴, 4 =일시정지, 5=영구정지
    WAITING(0),
    NOMAL(1),
    CERTIFIED(2),
    DORMANT(3),
    TEMPORARY_STOP(4),
    PERMANENT_STOP(5);

    private final int flag;
    ///////////////////////진우가 추가한 코드////////////////////////
    State(int flag) {
        this.flag = flag;
    }

    public static State fromInt(int i) {
        switch(i) {
            case 0: return WAITING;
            case 1: return NOMAL;
            case 2: return CERTIFIED;
            case 3: return DORMANT;
            case 4: return TEMPORARY_STOP;
            case 5: return PERMANENT_STOP;
            default:
                return NOMAL;
        }
    }

    public int getFlag() {
        return this.flag;
    }
}
