package web.mvc.config.user;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import web.mvc.entity.user.Users;
import web.mvc.repository.user.UserRepository;


import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// 필터는 @Compent 로 생성하지 않는다!!
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    //JWTUtil 주입
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager , JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;;
    }

    // AuthenticationManager에서 사용할 토큰을 만들고 저장한다!
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //클라이언트 요청에서 username, password 추출 이건 url의 ? 로 했을때 방식
        //String username = super.obtainUsername(request); // id
        //String password = super.obtainPassword(request); // pwd

        //폼으로 했을때 방식 name으로 값을 받아온다
        String username = request.getParameter("userId");
        String password = request.getParameter("userPwd");



        log.info("username {} " , username);
        log.info("password {} " , password);
        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함 // username, password , role 인수를 받는다  -> principal , credentials , authorities
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws  IOException{
        response.setContentType("text/html;charset=UTF-8");
        log.info("로그인 성공 ......");
        //UserDetailsS
        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority(); // ROLE_USER OR ROLE_ADMIN 저장한 ROLL이 나온다

        // 토큰 생성과정... 토큰 만료시간 1시간으로 설정 3600 * 1000ㅣ
        String token = jwtUtil.createJwt(customMemberDetails.getUsers(), role, 3600*1000L); //시간 설정 MS 1000L = 1초 , 1000L * 60 = 1분

        // 응답할 헤더 설정
        response.addHeader("Authorization", "Bearer " + token);

        Map<String, Object> map = new HashMap<>();
        Users users= customMemberDetails.getUsers();

        map.put("userId",users.getUserId() );
        map.put("userName",users.getUserName() );
        map.put("userSeq",users.getUserSeq() );
        


        Gson gson= new Gson();

        String arr = gson.toJson(map);
        response.getWriter().print(arr);
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        log.info("로그인 실패... ......");
        //로그인 실패시 401 응답 코드 반환
        response.setStatus(401);

        Map<String, Object> map = new HashMap<>();
        map.put("errMsg","인증되지 않은 사용자 입니다.");

        Gson gson= new Gson();

        String arr = gson.toJson(map);
        response.getWriter().print(arr);
    }
}