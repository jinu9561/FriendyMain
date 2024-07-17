package web.mvc.enums.chatting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartyBoardStatus {

      NORMAL(0)
    , END(1);

    private final int flag;

}
