package web.mvc.config.user;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


import java.util.Collections;

@Configuration
@EnableWebSecurity // Security 옵션 설정 enable, HTTP 요청에 대해 보안 필터 체인을 설정
@RequiredArgsConstructor
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final CustomLogoutHandler customLogoutHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public ExceptionTranslationFilter exceptionTranslationFilter() {
        return new ExceptionTranslationFilter(authenticationEntryPoint());
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("myApp");
        return entryPoint;
    }


    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //CORS설정
        http .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource()
        {
            @Override public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                configuration.setAllowedMethods(Collections.singletonList("*"));
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Collections.singletonList("*"));
                configuration.setMaxAge(3600L);
                configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                return configuration;
            }
        })));




        // 모든 설정은 람다식으로 해야만 오류가 안난다 !! -> 약속된 정의

        //csrf disable -> csrf공격에 방어하기 위한 토큰 주고 받는 부분을 비활성화! -> 우리가 설정한 토큰만 주고받는게 아니라 기본으로 이 토큰을 주고받기 때문에 비활성화
        http.csrf((auth) -> auth.disable());
        //Form 로그인 방식 disable -> React, JWT인증 방식으로 변겨예정
        // disable를 설정하면 시큐리티의 UsernamePasswordAuthenticationFilter 비활성됨.
        http.formLogin((auth) -> auth.disable()); // 처음 실행했을때(설정없이) 로그인 폼 뜨는거 비활성화

        //http basic 인증 방식 disable 헤더에 아이디,비밀번호 넣어서 인증하는 방석을 disable 하겠다
        http.httpBasic((auth) -> auth.disable());

        //경로별 인가 작업 -> 각 요청 url에대한 시큐리티 설정
        http.authorizeHttpRequests((auth) ->
//                auth.requestMatchers( "/users", "/users/**", "/sms","/sms/**","/email/"
//                                ,"email/**") // ROLE을 설정안해서 기본 ROLE은 ROLE_USER만 permit이 가능하다,토큰없이는 보기만 가능 상세보기x
//                        .permitAll()
//                        .requestMatchers("/admin","/admin/**")
//                        //.hasRole("ADMIN") // Role설정  ROLE_ADMIN만 permit가능, ROLE_접두어가 자동으로 붙고 따로 설정안하면 ROLE_USER가 기본으로 붙는다 -> ROLE_""를 설정한다,
//                        .permitAll() //  임시로 모든 설정 허용 나중에 주석
//                        .anyRequest() // 그 외 모든 요청
//                        //.authenticated()); // 인증이 필요하다
//                        .permitAll()); // 임시로 모든 설정 허용 나중에 주석
        auth.anyRequest().permitAll()); // 임시로 모든 설정 허용        //(서버)세션 설정 - JWT를 이용 할것이기 때문에 stateless설정 -> 사용자 정보를 (서버)세션에 저장하지 않겠다 -> 토큰으로 주고 받겠다
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함)
        // addFilterAt은 UsernamePasswordAuthenticationFilter의 자리에 LoginFilter가 실행되도록 설정하는 것
        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // addFilterAt 매개변수 인자 필터 대체
        // addFilterBefore  매개변수 인자 필터 전에 추가
        // addFilterAfter   매개변수 인자 필터 후에 추가

        // 토큰 만료에러 잡기
        http.exceptionHandling(excetionHanding->excetionHanding.authenticationEntryPoint(jwtAuthenticationEntryPoint));



        // logout 했을때 해야되는 기능
        http.logout(logout ->
                logout.logoutUrl("/logout") // 로그아웃요청 url
                        .addLogoutHandler(customLogoutHandler)
                        .logoutSuccessUrl("/users/logout")); // 로그 아웃 성공시 가야되는 url

        return http.build();
    }

}
