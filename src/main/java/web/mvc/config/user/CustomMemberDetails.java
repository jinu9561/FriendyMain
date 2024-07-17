package web.mvc.config.user;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import web.mvc.entity.user.Users;

import java.util.ArrayList;
import java.util.Collection;


@Slf4j
public class CustomMemberDetails implements UserDetails{

    @Getter
    private Users users;


    public CustomMemberDetails(Users users) {
        this.users = users;
        log.info("users: {}", users);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return "";
//            }
//        });
        // 람다로 바꾸기
        authorities.add(()->users.getRole());

        log.info("getAuthorities..,{}", authorities);

        return authorities;
    }

    @Override
    public String getPassword() {
        log.info("getPassword..");
        return users.getUserPwd();
    }

    @Override
    public String getUsername() { // Username은 id이다!
        log.info("getUsername..");
        return users.getUserId();
    }

    /***
     * 기능: 계정이 만료되지 않았는지를 나타냅니다.
     * 설명: 이 메서드가 true를 반환하면, 사용자의 계정이 만료되지 않았음을 의미합니다. 만료된 계정은 로그인이 불가능합니다.
     */
    @Override
    public boolean isAccountNonExpired() {
        log.info("isAccountNonExpired..");
        return true;
    }

    /**
     * 기능: 계정이 잠겨 있지 않았는지를 나타냅니다.
     * 설명: 이 메서드가 true를 반환하면, 사용자의 계정이 잠겨 있지 않음을 의미합니다. 잠긴 계정은 로그인이 불가능합니다.
     * */
    @Override
    public boolean isAccountNonLocked() {
        log.info("isAccountNonLocked..");
        return true;
    }

    /**
     * 기능: 자격 증명이 만료되지 않았는지를 나타냅니다.
     * 설명: 이 메서드가 true를 반환하면, 사용자의 자격 증명(예: 비밀번호)이 만료되지 않았음을 의미합니다. 만료된 자격 증명으로는 로그인이 불가능합니다.
     * */
    @Override
    public boolean isCredentialsNonExpired() {
        log.info("isCredentialsNonExpired..");
        return true;
    }

    /**
     * 기능: 계정이 활성화되었는지를 나타냅니다.
     * 설명: 이 메서드가 true를 반환하면, 사용자의 계정이 활성화되어 있음을 의미합니다. 비활성화된 계정은 로그인이 불가능합니다.
     * */
    @Override
    public boolean isEnabled() {
        log.info("isEnabled..");
        return true;
    }

    /**
     * 각 메서들의 반환값을 true로 준 이유??
     *
     * 이 메서드들이 모두 true를 반환할 때,
     * 사용자는 정상적으로 인증을 받고 애플리케이션에 접근할 수 있게 됩니다.
     * 이는 계정 상태와 관련된 모든 검사를 통과한 것으로 간주되기 때문입니다.
     * */
}
