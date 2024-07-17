package web.mvc.config.user;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import web.mvc.entity.user.Users;


import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    public JWTFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
				
        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");
				
        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response); // 다음 필터로 가고 없으면 servlet으로 간다
						
            //조건이 해당되면 메소드 종료 (필수)
            return; 
        }
			
        System.out.println("authorization now");
        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1]; // 공백으로 0번지 1번지 잘라내서 1번지만 사용한다 -> 순수토큰 사용
			
        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");

            // 브라우져로 리플래쉬토큰을 요청
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        
        //토큰에서 username과 role 획득
        String userName = jwtUtil.getUsername(token);
        String userId = jwtUtil.getId(token);
        String role = jwtUtil.getRole(token);
        Long userSeq = jwtUtil.getUserSeq(token);
        
        //userEntity를 생성하여 값 set
        Users users = new Users();
        users.setUserSeq(userSeq);
        users.setUserId(userId);
        users.setUserName(userName);
        users.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        CustomMemberDetails customMemberDetails = new CustomMemberDetails(users);

        //스프링 시큐리티 인증 토큰 생성 userDetail, password , role 인수를 받는다  -> principal , credentials , authorities
        Authentication authToken = new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());
        //세션에 사용자 등록 - 세션이 만들어짐.
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}